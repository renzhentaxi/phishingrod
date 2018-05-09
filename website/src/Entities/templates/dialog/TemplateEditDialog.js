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
import {bind} from "./ParameterDialog";

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
    </TableRow>);
}

const FIELDS = ["name", "html"];

const DEFAULT_STATE = attachDataState({}, ...FIELDS);
DEFAULT_STATE.openAdvanced = false;

export class TemplateEditDialog extends React.Component {

    constructor() {
        super();
        this.state = Object.assign({}, DEFAULT_STATE);

        this.initialize = this.initialize.bind(this);
        this.reset = this.reset.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleSave = this.handleSave.bind(this);
        bind(this, "openAdvanced", "closeAdvanced", "onSaveAdvanced")
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

    openAdvanced() {
        this.setState({openAdvanced: true});
        this.advancedEditorRef.current.init(this.state.data.html);
    }

    closeAdvanced() {
        this.setState({openAdvanced: false});
    }

    onSaveAdvanced(html, parameters) {
        this.closeAdvanced();
        this.setState(DataUpdater("html", html));
        this.setState(DataUpdater("parameters", parameters));

    }

    handleClose() {
        this.props.onClose();
        this.reset();
    }

    reset() {
        this.setState(DEFAULT_STATE);
    }

    readRows = ["id", "createdOn", "lastModifiedOn"];
    advancedEditorRef = React.createRef();

    render() {
        const {open} = this.props;
        const {hasError, errors, data, openAdvanced} = this.state;

        const readTextFields = this.readRows.map(name => <BasicRow key={name} data={[name, data[name]]}/>);
        return (
            <Dialog open={open} onClose={this.handleClose} maxWidth={false}>
                <DialogTitle style={{textAlign: "center"}}>Edit Template</DialogTitle>
                <TemplateSmartEditDialog ref={this.advancedEditorRef} open={openAdvanced} onClose={this.closeAdvanced}
                                         onSave={this.onSaveAdvanced}/>
                <Table>
                    <TableBody>
                        {readTextFields}
                        <SmartTextFieldRow name="name" value={data.name} onChange={this.handleChange}
                                           errorState={errors}/>
                        <SmartTextFieldRow name="html" value={data.html} onChange={this.handleChange}
                                           errorState={errors} line={20}/>
                        <ButtonRow data={
                            [
                                {text: "Advanced", onClick: this.openAdvanced, disabled: false},
                                {text: "Save", onClick: this.handleSave, disabled: hasError},
                            ]
                        }/>
                    </TableBody>
                </Table>
            </Dialog>
        )
    }
}

