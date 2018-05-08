import React from 'react';
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
import Button from "material-ui/es/Button/Button";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import update from 'immutability-helper';
import {SmartTextFieldRow} from "../SmartTextField";
import {Parameter} from "./Parameter";
import {AddParameterRow} from "./AddParameterRow";
import {
    AddErrorUpdater,
    attachErrorState,
    DeleteErrorUpdater,
    smartSetState,
    smartValidate
} from "../../../ErrorHandler";
import {DeleteParameterUpdater, SetParameterUpdater} from "../../../ParameterHandler";

function ButtonRow(props) {
    return (<TableRow>
        <TableCell colSpan="3" style={{textAlign: "center", width: "100%"}}>
            <Button onClick={props.onClick} variant="raised" color="primary" disabled={props.disabled}
                    style={{width: "100%"}}>{props.text}</Button>
        </TableCell>
    </TableRow>);
}

const DEFAULT_STATE = attachErrorState(
    {
        data: {
            emailAddress: "",
            parameters: {}
        },
    },
    "emailAddress",
    "parameters"
);

export class TargetEditDialog extends React.Component {

    constructor() {
        super();
        this.state = Object.assign({}, DEFAULT_STATE);

        this.reset = this.reset.bind(this);

        this.validateEmailAddress = this.validateEmailAddress.bind(this);
        this.validateParameter = this.validateParameter.bind(this);
        this.getAllParameterValidators = this.getAllParameterValidators.bind(this);
        this.handleEmailChange = this.handleEmailChange.bind(this);

        this.checkDuplicate = this.checkDuplicate.bind(this);
        this.handleAddParameter = this.handleAddParameter.bind(this);
        this.handleDeleteParameter = this.handleDeleteParameter.bind(this);
        this.handleChangeParameter = this.handleChangeParameter.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleSave = this.handleSave.bind(this);
    }

    validateParameter(name) {
        return () => {
            const value = this.state.data.parameters[name];
            const result = {type: "param_" + name};
            if (value.trim().length === 0)
                result.message = `parameter ${name} can not be empty`;
            return result;
        };
    }

    getAllParameterValidators() {
        return Object.keys(this.state.data.parameters).map(name => this.validateParameter(name));
    }

    validateEmailAddress() {
        const {emailAddress} = this.state.data;
        const result = {type: "emailAddress"};
        if (emailAddress.trim().length === 0) {
            result.message = "Email address can not be empty";
        }
        return result;
    }

    handleEmailChange(label, emailAddress) {
        const updater = (state) => update(state, {data: {emailAddress: {$set: emailAddress}}});
        smartSetState(this, updater, this.validateEmailAddress);
    }

    handleChangeParameter(name, value) {
        smartSetState(this, SetParameterUpdater(name, value), this.validateParameter(name));
    }

    handleAddParameter(name, value) {
        this.setState(AddErrorUpdater("param_" + name), () => this.setState(SetParameterUpdater(name, value)));
    }

    handleDeleteParameter(name) {
        this.setState(DeleteParameterUpdater(name));
        this.setState(DeleteErrorUpdater("param_" + name));
    }

    initalize(data) {
        const {parameters} = data;
        for (const paramName of Object.keys(parameters)) {
            this.setState(AddErrorUpdater("param_" + paramName));
        }

        this.setState((state) => update(state, {data: {$set: data}}));
    }

    checkDuplicate(name) {
        return this.state.data.parameters.hasOwnProperty(name);
    }

    handleSave() {
        const {data} = this.state;
        if (smartValidate(this, this.validateEmailAddress, ...this.getAllParameterValidators())) {
            this.props.onSave(data);
            this.props.onClose();
            this.reset();
        }
    }

    handleClose() {
        this.props.onClose();
        this.reset();
    }

    reset() {
        this.setState(DEFAULT_STATE);
    }

    genParameterRows() {
        const {parameters} = this.state.data;
        const paramList = [];
        for (let paramName of Object.keys(parameters)) {
            const paramValue = parameters[paramName];
            const errorType = "param_" + paramName;
            paramList.push(
                <Parameter
                    key={paramName}
                    name={paramName}
                    value={paramValue}
                    errors={[this.state.errors[errorType]]}
                    onDelete={this.handleDeleteParameter}
                    onChange={this.handleChangeParameter}
                />);
        }
        return paramList;
    }

    render() {
        const {open, data} = this.props;
        const {id, createdOn, lastModifiedOn, emailAddress} = this.state.data;
        const hasError = this.state.hasError;
        const emailError = this.state.errors.emailAddress;

        return (
            <Dialog open={open} onClose={this.handleClose} maxWidth="md">
                <DialogTitle style={{textAlign: "center"}}>Add Dialog</DialogTitle>
                <Table>
                    <TableBody>
                        <SmartTextFieldRow label="Id" value={id} disabled/>
                        <SmartTextFieldRow label="CreatedOn" value={createdOn} disabled/>
                        <SmartTextFieldRow label="LastModifiedOn" value={lastModifiedOn} disabled/>
                        <SmartTextFieldRow label="Email Address" value={emailAddress} onChange={this.handleEmailChange}
                                           errors={[emailError]}/>
                        {this.genParameterRows()}
                        <AddParameterRow
                            onAdd={this.handleAddParameter}
                            checkDuplicate={this.checkDuplicate}/>
                        <ButtonRow onClick={this.handleSave} text="save" disabled={hasError}/>
                    </TableBody>
                </Table>
            </Dialog>
        )
    }
}

