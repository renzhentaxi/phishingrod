export function upperCase(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

/**
 * splits every single text that is a child of this element into words
 * each word is wrapped with a <split><//split> tag
 * @param element
 * @param splitTag the tag to use to wrap the splited words. defaults to "split"
 */
export function splitWords(element, splitTag = "split") {
    const walker = document.createTreeWalker(element, NodeFilter.SHOW_TEXT);
    const nodes = [];
    while (walker.nextNode()) {
        if (walker.currentNode.parentElement.tagName !== "PARAMNODE")
            nodes.push(walker.currentNode);
    }

    for (let node of nodes) {
        let remaining = node.textContent;
        let result;
        while (remaining.trim().length !== 0) {
            result = /\w+|[\S]/.exec(remaining);
            const start = result.index;
            const end = result[0].length;
            node = node.splitText(start);
            const before = node;
            node = node.splitText(end);
            wrap(splitTag, before);
            remaining = remaining.substring(start + end);
        }
    }
}

export function wrap(tag, element) {
    const wrapper = document.createElement(tag);
    element.parentElement.replaceChild(wrapper, element);
    wrapper.appendChild(element);
    return wrapper;
}

export function unwrap(element) {
    const c = element.firstChild;
    element.parentElement.replaceChild(c, element);
    return c;
}

export function merge(tag, elements) {
    let unwrapped = [];
    for (let e of elements) {
        e = unwrap(e);
        unwrapped.push(e);
    }
    let parent = wrap(tag, unwrapped[0]);

    unwrapped.forEach(e => {
        if (e !== parent)
            parent.appendChild(e)
    });
    console.log(unwrapped);
}

export function switchWrap(tag, element) {
    return wrap(tag, unwrap(element));
}

export function unwrapAll(tag, element) {
    const walker = document.createTreeWalker(element, NodeFilter.SHOW_ELEMENT);
    const nodes = [];
    while (walker.nextNode()) {
        let node = walker.currentNode;
        if (node.tagName === tag) {
            nodes.push(node);
        }
    }
    for (let n of nodes) {
        unwrap(n);
    }

}