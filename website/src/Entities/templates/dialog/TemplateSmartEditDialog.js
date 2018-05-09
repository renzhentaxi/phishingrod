import React from 'react';
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
import {splitWords, switchWrap, unwrapAll} from "../../../util/util";
import {ParameterDialog} from "./ParameterDialog";
import Button from "material-ui/es/Button/Button";

const expandToMax = {height: "100%", width: "100%"};

export function bind(thisArg, func) {
    thisArg[func.name] = thisArg[func.name].bind(thisArg);
}

export class TemplateSmartEditDialog extends React.Component {

    htmlRef = React.createRef();
    state =
        {
            openParamDialog: false,
        };

    constructor() {
        super();
        bind(this, this.onLoad);
        bind(this, this.select);
        bind(this, this.closeParameterizationDialog);
        bind(this, this.handleAdd);
    }


    select(element) {
        if (element.tagName === "SPLIT") {
            element = switchWrap("paramNode", element);
        }
        this.selected = element;
        this.openParameterizationDialog();
    }

    onLoad() {
        const document = this.htmlRef.current.contentDocument;
        const {head, body} = document;
        splitWords(body);
        const style = document.createElement("style");
        style.id = "tempStyle";
        style.innerText = "paramNode{border-style:dotted;}";
        head.appendChild(style);
        body.addEventListener("click", (event) => {
            const {target} = event;
            if (target.tagName === "SPLIT" || target.tagName === "PARAMNODE") {
                console.log(target);
                this.select(target);
            }
            event.preventDefault();
            event.stopPropagation();
        });

    }

    init(html) {
        this.setState({html});
    }

    openParameterizationDialog() {
        this.setState({openParamDialog: true})
    }

    closeParameterizationDialog() {
        this.setState({openParamDialog: false})
    }

    handleAdd() {
        this.closeParameterizationDialog();
    }

    render() {
        const {open, onClose, onSave} = this.props;
        const html = this.state.html;
        const {openParamDialog} = this.state;
        return (
            <Dialog open={open} onClose={onClose} fullScreen>
                <DialogTitle style={{textAlign: "center"}}>Template Editor</DialogTitle>
                <iframe title="html" ref={this.htmlRef} srcDoc={html} style={expandToMax} onLoad={this.onLoad}/>

                <ParameterDialog open={openParamDialog} onClose={this.closeParameterizationDialog}
                                 selected={this.selected}
                                 onAdd={this.handleAdd}/>
                <Button variant="raised" color="primary" style={{width: "100%"}} onClick={() => {
                    const doc = this.htmlRef.current.contentDocument;
                    onSave(...cleanHtml(doc));
                }
                }> Save </Button>
            </Dialog>);
    }
}

function cleanHtml(doc) {
    const docRoot = doc.documentElement;
    const style = doc.getElementById("tempStyle");
    style.parentElement.removeChild(style);
    const head = doc.head;
    const body = doc.body;
    if (head.childElementCount === 0) {
        head.parentElement.removeChild(head);
    }
    unwrapAll("SPLIT", docRoot);
    const params = {"phishingTarget": new Set(), "spoofTarget": new Set()};
    for (const p of doc.querySelectorAll("paramNode")) {
        const source = p.getAttribute("source");
        const name = p.getAttribute("name");
        params[source].add(name);
    }
    const realParams = [];
    for (const source of Object.keys(params))
        for (const name of params[source])
            realParams.push({source, name})
    return [docRoot.innerHTML, realParams];
}
