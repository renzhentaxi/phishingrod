import React from 'react';
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
import Button from "material-ui/es/Button/Button";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import {
    attachErrorState,
    createValidator,
    notEmptyValidator,
    smartSetState,
    smartValidate
} from '../../../util/ErrorHandler';
import {SmartTextFieldRow} from "../../ui/SmartTextField";
import {DataUpdater} from "../../../util/Updaters";

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
            name: "",
            password: "",
            server: ""
        },
    },
    "name",
    "password",
    "server"
);

const FIELDS = ["name", "password", "server"];

export class SenderAddDialog extends React.Component {

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
            password: createValidator(this, "password", notEmptyValidator),
            server: createValidator(this, "server", notEmptyValidator),
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
            <Dialog open={open} onClose={this.handleClose} maxWidth="md">
                <DialogTitle style={{textAlign: "center"}}>Add Dialog</DialogTitle>
                <Table>
                    <TableBody>
                        {
                            FIELDS.map(name => <SmartTextFieldRow key={name} name={name}
                                                                  onChange={this.handleChange}
                                                                  errorState={errors}/>)
                        }
                        <ButtonRow onClick={this.handleAdd} text="add" disabled={hasError}/>
                    </TableBody>
                </Table>
            </Dialog>
        )
    }
}

