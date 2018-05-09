import React from 'react';
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
import InputLabel from "material-ui/es/Input/InputLabel";
import Select from "material-ui/es/Select/Select";
import MenuItem from "material-ui/es/Menu/MenuItem";
import {SmartTextField} from "../../ui/SmartTextField";
import Button from "material-ui/es/Button/Button";
import {switchWrap} from "../../../util/util";

export function bind(thisArg, ...funcs) {
    for (let func of funcs) {
        thisArg[func] = thisArg[func].bind(thisArg);
    }
}

export class ParameterDialog extends React.Component {

    constructor() {
        super();
        bind(this, "handleChange", "handleNameChange");
    }

    handleChange(event) {
        this.selected.setAttribute("source", event.target.value);
        this.forceUpdate();
    }

    handleNameChange(name, value) {
        this.selected.setAttribute("name", value);
        this.forceUpdate();
    }

    render() {
        const {open, onClose, onAdd, selected} = this.props;
        if (selected) {
            this.selected = selected;
            const source = selected.getAttribute("source") || "phishingTarget";
            const name = selected.getAttribute("name") || "";
            const nameError = name.trim().length === 0;
            return (
                <Dialog open={open} onClose={onClose}>
                    <DialogTitle>Parameterize {selected.textContent} </DialogTitle>
                    <InputLabel>Source</InputLabel>
                    <Select value={source} onChange={this.handleChange}>
                        <MenuItem value="phishingTarget">
                            Phishing Target
                        </MenuItem>
                        <MenuItem value="spoofTarget">
                            Spoof Target
                        </MenuItem>
                    </Select>
                    <SmartTextField name="name" value={name} errorState={{
                        name: {
                            error: nameError,
                            message: nameError ? "Name can not be empty" : ""
                        }
                    }}
                                    onChange={this.handleNameChange}/>

                    <Button variant="raised" color="primary" disabled ={nameError}onClick={() => {
                        if (!selected.getAttribute("source")) selected.setAttribute("source", "phishingTarget");
                        if (!selected.getAttribute("name")) selected.setAttribute("name", "");
                        onAdd();
                    }}> Save </Button>
                    <Button variant="raised" color="primary"
                            onClick={() => {
                                switchWrap("split", this.selected);
                                onAdd();
                            }}> Delete </Button>
                </Dialog>);
        }
        return null;
    }
}