import React from 'react';
import test from "./test.html";
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";

console.log(test);

export class TemplateSmartEditDialog extends React.Component {

    render() {
        return (
            <Dialog open={open} onClose={this.handleClose} maxWidth={false}>
                <DialogTitle style={{textAlign: "center"}}>Edit Template</DialogTitle>
            </Dialog>);
    }
}