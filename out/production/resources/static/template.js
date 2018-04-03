const template = document.querySelector("#source");

template.onload = function (ev) {

    const body = template.contentDocument.body;

    let treewalker = document.createTreeWalker(body, NodeFilter.SHOW_TEXT);

    let elementSet = new Set();
    while (treewalker.nextNode()) {
        let currentNode = treewalker.currentNode;
        if (currentNode.textContent.trim().length !== 0) {
            elementSet.add(currentNode.parentElement);
        }
    }

    applyStyle(template.contentDocument);
    elementSet = parseElements(elementSet);
    installOnClick(elementSet);
    installContextMenu(template.contentDocument);
};


//applies style to the template
function applyStyle(doc) {

    const head = doc.querySelector("head");
    let link = doc.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'sourceStyle.css';
    head.appendChild(link);
}

function applyScript(bodyElement, source) {
    let script = document.createElement('script');
    script.src = source;
    bodyElement.appendChild(script);
}


//processes the elements for easier manipulation
function parseElements(elements) {

    let parsedElementSet = new Set();

    for (let el of elements) {
        let children = el.childNodes;
        // if (!children) {
        //     console.log(("no children"));
        // } else if (children.length === 1) {
        //     console.log("single children");
        // } else {
        //     console.log("more than one children");
        // }
        for (let childElement of children) {
            if (childElement.textContent.length === 0) continue;

            let old = childElement;
            childElement = wrap(childElement, "pr-node");
            if (old.tagName === 'A') {
                childElement.setAttribute('pr-node-type', 'link');
            } else if (el.tagName === 'A') {
                // childElement.setAttribute('pr-node-type', 'link-text');
            }
            else {
                childElement.setAttribute('pr-node-type', 'text');
            }
            parsedElementSet.add(childElement);
        }
    }
    return parsedElementSet;
}

function wrap(child, className, wrapper) {
    let newElement = document.createElement(wrapper || 'div');
    newElement.setAttribute("class", className);
    child.parentElement.replaceChild(newElement, child);
    newElement.appendChild(child);
    return newElement;
}

let menuState = 0;
let activeMenu = "context-menu--active";
let menu = document.querySelector("#context-menu");

function showMenu(show) {
    if (show && menuState !== 1) {
        menuState = 1;
        menu.classList.add(activeMenu);
    } else if (!show && menuState === 1) {

        menuState = 0;
        menu.classList.remove(activeMenu);
    }
}

function getPosition(e) {
    let posx = 0;
    let posy = 0;

    if (!e) {
        let e = window.event;
    }

    if (e.pageX || e.pageY) {
        posx = e.pageX;
        posy = e.pageY;
    } else if (e.clientX || e.clientY) {
        posx = e.clientX + document.body.scrollLeft +
            document.documentElement.scrollLeft;
        posy = e.clientY + document.body.scrollTop +
            document.documentElement.scrollTop;
    }

    return {
        x: posx,
        y: posy
    }
}

function positionElement(element, position) {
    element.style.left = position.x + "px";
    element.style.top = position.y + "px";
}

//handles contextMenu
function installContextMenu(templateDocument) {
    templateDocument.addEventListener("contextmenu",
        function (ev) {
            if (clickInsideElement(ev, "pr-node")) {
                ev.preventDefault();
                showMenu(true);
                positionElement(menu, getPosition(ev));
            } else {
                showMenu(false);
            }
        }
    )

}

function clickInsideElement(e, className) {
    let el = e.srcElement || e.target;

    if (el.classList.contains(className)) {
        return el;
    } else {
        while (el = el.parentNode) {
            if (el.classList && el.classList.contains(className)) {
                return el;
            }
        }

    }
    return false;
}


//handles clicking on selected node
function installOnClick(elementSet) {
    console.log("installing onclick on");
    for (let el of elementSet) {
        el.onclick = function (ev) {
            ev.preventDefault();
            onClickElement(el);
        }
    }
}

function onClickAnchor(elementClicked) {
    console.log(elementClicked + " i was clicked");
}

function onClickText(elementClick) {
    console.log(elementClick + " i was clicked22");
}

function onClickElement(elementClicked) {
    const tag = elementClicked.tagName;
    const textTags = ['SPAN', 'STRONG'];
    if (textTags.indexOf(tag) !== -1) {
        onClickText(elementClicked);
    }
    else if (elementClicked.tagName === 'A') {
        onClickAnchor(elementClicked);
    }
    parameterize(elementClicked, "test param");
}


function createParam(element) {
    let paramEl = wrap(element, 'pr-param');
    wrap(paramEl.firstChild, 'original');
    let nameEl = document.createElement("div");
    let inputEl = document.createElement("input");

    nameEl.classList.add("name");
    inputEl.classList.add("input");

    inputEl.onkeypress = function (ev) {
        if (!ev) {
            ev = window.event;
        }
        let keycode = ev.keyCode || ev.which;
        if (keycode === 13) {
            paramEl.setAttribute("pr-param-name", inputEl.value);
            nameEl.textContent = "Param:" + inputEl.value;
            console.log("you entered something");
        }
    };


    paramEl.appendChild(nameEl);
    paramEl.appendChild(inputEl);

    paramEl.classList.add("showInput");
    return paramEl;
}

//parameterization
function parameterize(element) {
    let child = element.firstChild;
    if (child.nodeType === Node.TEXT_NODE) {
        createParam(child);
    }
    // wrap(element.firstChild, 'pr-param-original');
    // let param_input = createParam();
    // element.appendChild(param_input);

    // console.log(element.childNodes);

    // element.innerText = " [" + paramName + ": " + element.innerText + "] ";

}
