import React from 'react';
import TableRow from "material-ui/es/Table/TableRow";
import {SmartTextField} from "../SmartTextField";
import TableCell from "material-ui/es/Table/TableCell";
import {AddButton} from "../../ui/Buttons";
import update from "immutability-helper";
import {attachErrorState, smartSetState, smartValidate} from "../../../util/ErrorHandler";

const DEFAULT_STATE = attachErrorState(
    {
        name: "",
        value: "",
    },
    "name", "value");

export class AddParameterRow extends React.Component {
    constructor() {
        super();
        this.state = Object.assign({}, DEFAULT_STATE);
        this.handleChange = this.handleChange.bind(this);
        this.handleAdd = this.handleAdd.bind(this);
        this.validateValue = this.validateValue.bind(this);
        this.validateName = this.validateName.bind(this);
    }

    validateName() {
        const {checkDuplicate} = this.props;
        const {name} = this.state;
        const result = {type: "name"};

        if (name.trim().length === 0) {
            result.message = "Name can not be empty";
        } else if (checkDuplicate(name)) {
            result.message = "Paramter with the same name already exist";
        }
        return result;
    }

    validateValue() {
        const {value} = this.state;
        const result = {type: "value"};
        if (value.trim().length === 0) {
            result.message = "Value can not be empty"
        }
        return result;
    }

    handleChange(label, value) {
        const newState = update(this.state, {[label]: {$set: value}});
        switch (label) {
            case "name":
                smartSetState(this, newState, this.validateName);
                break;
            case "value":
                smartSetState(this, newState, this.validateValue);
                break;
            default:
                console.error("invalid label");
        }
    }

    handleAdd() {
        if (smartValidate(this, this.validateValue, this.validateName)) {
            const {name, value} = this.state;
            this.props.onAdd(name, value);
            this.reset()
        }
    }

    reset() {
        this.setState(DEFAULT_STATE)
    }

    render() {
        const {name, value, errors: {name: nameError, value: valueError}} = this.state;
        return (
            <TableRow>
                <TableCell>
                    <SmartTextField label="name" errors={[nameError]} value={name} onChange={this.handleChange}/>
                </TableCell>
                <TableCell>
                    <SmartTextField label="value" errors={[valueError]} value={value} onChange={this.handleChange}/>
                </TableCell>
                <TableCell>
                    <AddButton onClick={this.handleAdd} disabled={this.state.hasError}/>
                </TableCell>
            </TableRow>
        )
    }
}