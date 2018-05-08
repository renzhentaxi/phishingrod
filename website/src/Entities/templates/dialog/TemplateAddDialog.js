import React from 'react';
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
import Button from "material-ui/es/Button/Button";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import {
    attachDataState,
    createValidator,
    notEmptyValidator,
    smartSetState,
    smartValidate
} from '../../../util/ErrorHandler';
import {DataUpdater} from "../../../util/Updaters";
import {SmartTextFieldRow} from "../../ui/SmartTextField";

function ButtonRow(props) {
    return (<TableRow>
        <TableCell colSpan="3" style={{textAlign: "center", width: "100%"}}>
            <Button onClick={props.onClick} variant="raised" color="primary" disabled={props.disabled}
                    style={{width: "100%"}}>{props.text}</Button>
        </TableCell>
    </TableRow>);
}

const FIELDS = ["name", "html"];

const DEFAULT_STATE = attachDataState({}, ...FIELDS);

export class TemplateAddDialog extends React.Component {

    constructor() {
        super();
        this.state = Object.assign({}, DEFAULT_STATE);

        this.reset = this.reset.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleAdd = this.handleAdd.bind(this);
    }

    validators =
        {
            name: createValidator(this, "name", notEmptyValidator),
            html: createValidator(this, "html", notEmptyValidator),
        };

    validatorList = Object.values(this.validators);


    handleChange(name, value) {
        smartSetState(this, DataUpdater(name, value), this.validators[name]);
    }

    handleAdd() {
        const {data} = this.state;
        if (smartValidate(this, ...this.validatorList)) {
            this.props.onAdd(data);
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

    render() {
        const {open} = this.props;
        const {hasError, errors} = this.state;
        return (
            <Dialog open={open} onClose={this.handleClose} maxWidth={false}>
                <DialogTitle style={{textAlign: "center"}}>Add Template</DialogTitle>
                <Table>
                    <TableBody>
                        <SmartTextFieldRow name="name" onChange={this.handleChange} errorState={errors}/>
                        <SmartTextFieldRow name="html" onChange={this.handleChange} errorState={errors} line={20}/>
                        <ButtonRow onClick={this.handleAdd} text="add" disabled={hasError}/>
                    </TableBody>
                </Table>
            </Dialog>
        )
    }
}

