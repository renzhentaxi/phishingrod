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
import {SmartTextFieldRow} from "../../ui/SmartTextField";
import {DataUpdater} from "../../../util/Updaters";
import {BasicRow} from "../../ui/SmartRow";

function ButtonRow(props) {
    return (<TableRow>
        <TableCell colSpan="3" style={{textAlign: "center", width: "100%"}}>
            <Button onClick={props.onClick} variant="raised" color="primary" disabled={props.disabled}
                    style={{width: "100%"}}>{props.text}</Button>
        </TableCell>
    </TableRow>);
}

const FIELDS = ["name", "password", "server"];

const DEFAULT_STATE = attachDataState({}, ...FIELDS);

export class SenderEditDialog extends React.Component {

    constructor() {
        super();
        this.state = Object.assign({}, DEFAULT_STATE);

        this.initialize = this.initialize.bind(this);
        this.reset = this.reset.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleSave = this.handleSave.bind(this);
    }

    validators =
        {
            name: createValidator(this, "name", notEmptyValidator),
            password: createValidator(this, "password", notEmptyValidator),
            server: createValidator(this, "server", notEmptyValidator),
        };

    validatorList = Object.values(this.validators);


    initialize(data) {
        this.setState({data});
    }

    handleChange(name, value) {
        smartSetState(this, DataUpdater(name, value), this.validators[name]);
    }

    handleSave() {
        const {data} = this.state;
        if (smartValidate(this, ...this.validatorList)) {
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

    readRows = ["id", "createdOn", "lastModifiedOn"];

    render() {
        const {open} = this.props;
        const {hasError, errors, data} = this.state;
        const readTextFields = this.readRows.map(name => <BasicRow key={name} data={[name, data[name]]}/>);
        const editTextFields = FIELDS.map(name => <SmartTextFieldRow key={name}
                                                                     name={name}
                                                                     value={data[name]}
                                                                     onChange={this.handleChange}
                                                                     errorState={errors}/>);
        return (
            <Dialog open={open} onClose={this.handleClose} maxWidth="md">
                <DialogTitle style={{textAlign: "center"}}>Edit Sender</DialogTitle>
                <Table>
                    <TableBody>
                        {readTextFields}
                        {editTextFields}
                        <ButtonRow onClick={this.handleSave} text="save" disabled={hasError}/>
                    </TableBody>
                </Table>
            </Dialog>
        )
    }
}

