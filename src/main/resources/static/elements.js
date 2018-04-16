export class Elements {

    static from(json) {
        let className = json.className;
        let tagName = json.tagName;
        const el = Elements.element(className, tagName);
        for (let p in json) {
            if (p !== "className" && p !== "tagName") {
                el.setAttribute(p, json[p]);
            }
        }
        return el;
    }

    static wrapChild(wrapperConfig, child) {
        const parent = child.parentElement;
        const wrapper = Elements.from(wrapperConfig);
        wrapper.classList.add("wrapper");
        parent.replaceChild(wrapper, child);
        wrapper.appendChild(child);
        return wrapper;
    }

    static wrap(wrapperConfig, ...elements) {
        const wrapper = Elements.from(wrapperConfig);
        for (let el of elements) {
            wrapper.appendChild(el);
        }
        return wrapper;
    }


    static wrapWithTag(wrapTag = "div", ...elements) {
        const wrapper = document.createElement(wrapTag);
        for (let el of elements) {
            wrapper.appendChild(el);
        }
        return wrapper;
    }

    static element(className, tagName = "div") {
        const el = document.createElement(tagName);
        if (className && className.className) className = className.className;
        if (className !== null)
            el.className = className;
        return el;
    }

    static text(text, className, tagName) {
        const textNode = document.createTextNode(text);
        if (className) {
            const el = Elements.element(className, tagName);
            el.appendChild(textNode);
            return el;
        }
        return textNode;

    }

    static button(text, className) {
        const btn = this.element(className, "button");
        btn.appendChild(document.createTextNode(text));
        return btn;
    }

    static input(placeHolder, className) {
        const input = this.element(className, "input");
        input.placeholder = placeHolder;
        return input;
    }

    static unwrap(wrapped) {
        console.log(wrapped);
        console.assert(wrapped.classList.contains("wrapper"));
        const child = wrapped.firstChild;
        const parent = wrapped.parentElement;
        parent.replaceChild(child, wrapped);
        parent.normalize();
    }

    static extractText(textNode, start, end) {
        const extracted = textNode.splitText(start);
        const x = extracted.splitText(end - start );
        return extracted;
    }

    row (...elements)
    {
        const row = Elements.element(null,"th");
        console.log(row);
    }
}


export default new Elements();