import React from 'react';
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
import Button from "material-ui/es/Button/Button";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import update from 'immutability-helper';
import {AddParameterRow} from "./AddParameterRow";
import {Parameter} from "./Parameter";
import {SmartTextFieldRow} from "../SmartTextField";

function ButtonRow(props) {
    return (<TableRow>
        <TableCell colSpan="3" style={{textAlign: "center", width: "100%"}}>
            <Button onClick={props.onClick} variant="raised" color="primary"
                    style={{width: "100%"}}>{props.text}</Button>
        </TableCell>
    </TableRow>);
}

class PhishingTargetAddDialog extends React.Component {

    state = {
        emailAddress: "",
        parameters: {}
    };

    constructor() {
        super();
        this.handleAddParameter = this.handleAddParameter.bind(this);
        this.handleChangeParameter = this.handleChangeParameter.bind(this);
        this.handleDeleteParameter = this.handleDeleteParameter.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.handleEmailChange = this.handleEmailChange.bind(this);
    }

    handleDeleteParameter(name) {
        const newState = update(this.state, {parameters: {$unset: [name]}});
        this.setState(newState);
    }

    handleChangeParameter(name, value, onError) {
        const newState = update(this.state, {parameters: {[name]: {$set: value}}});
        this.setState(newState);
        if (value.trim().length === 0) {
            onError("Value can not be empty")
        } else {
            return true;
        }
    }

    handleAddParameter(name, value, error) {
        const parameters = this.state.parameters;
        if (name.length === 0) {
            error("name", "Name can not be empty");
        }
        else if (parameters.hasOwnProperty(name)) {
            error("name", "Parameter already exist");
        }
        else if (value.trim().length === 0) {
            error("value", "Value can not be empty")
        }
        else {
            const newState = update(this.state, {parameters: {[name]: {$set: value}}});
            this.setState(newState);
            return true;
        }
    }

    clear() {
        this.setState({emailAddress: "", parameters: {}})
    }

    handleEmailChange(label, value, onError) {
        this.setState(update(this.state, {emailAddress: {$set: value}}));
        if (value.trim().length === 0) onError("Email Address can not be empty");
        else return true;
    }

    handleSave() {
        this.props.onAdd(this.state);
        this.props.onClose();
        this.clear();
    }

    getParameters() {
        const {parameters} = this.state;
        const paramList = [];
        for (let p in parameters) {
            paramList.push(<Parameter key={p} name={p} value={parameters[p]}
                                      onDelete={this.handleDeleteParameter}
                                      onChange={this.handleChangeParameter}/>)
        }
        return paramList;
    }

    render() {
        const {open, onClose} = this.props;
        return (
            <Dialog open={open} onClose={onClose} maxWidth="md">
                <DialogTitle style={{textAlign: "center"}}>Add Dialog</DialogTitle>
                <Table>
                    <TableBody>
                        <SmartTextFieldRow label="Email Address" onChange={this.handleEmailChange}/>
                        {this.getParameters()}
                        <AddParameterRow onAdd={this.handleAddParameter}/>
                        <ButtonRow onClick={this.handleSave} text="add"/>
                    </TableBody>
                </Table>
            </Dialog>
        )
    }
}

export default PhishingTargetAddDialog;
