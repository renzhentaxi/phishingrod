import React from 'react';
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
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
import Button from "material-ui/es/Button/Button";
import {TemplateSmartEditDialog} from "./TemplateSmartEditDialog";

function ButtonRow(props) {
    const {data} = props;
    const cells = data.map(btn =>
        <TableCell key={btn.text} style={{textAlign: "center"}}>
            <Button
                onClick={btn.onClick}
                variant="raised"
                color="primary"
                disabled={props.disabled}
                style={{width: "100%"}}>
                {btn.text}
            </Button>
        </TableCell>);

    return (<TableRow>
        {cells}
        {/*<Button onClick={props.onClick} variant="raised" color="primary" disabled={props.disabled}*/}
        {/*style={{width: "100%"}}>{props.text}</Button>*/}
    </TableRow>);
}

const FIELDS = ["name", "html"];

const DEFAULT_STATE = attachDataState({}, ...FIELDS);


export class TemplateEditDialog extends React.Component {

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
            html: createValidator(this, "html", notEmptyValidator),
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

    handleAdvanced() {

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
        return (
            <Dialog open={open} onClose={this.handleClose} maxWidth={false}>
                <DialogTitle style={{textAlign: "center"}}>Edit Template</DialogTitle>
                <TemplateSmartEditDialog/>
                <Table>
                    <TableBody>
                        {readTextFields}
                        <SmartTextFieldRow name="name" value={data.name} onChange={this.handleChange}
                                           errorState={errors}/>
                        <SmartTextFieldRow name="html" value={data.html} onChange={this.handleChange}
                                           errorState={errors} line={20}/>
                        <ButtonRow data={
                            [
                                {text: "Advanced", onClick: this.handleAdvanced, disabled: false},
                                {text: "Save", onClick: this.handleSave, disabled: hasError},
                            ]
                        }/>
                    </TableBody>
                </Table>
            </Dialog>
        )
    }
}

