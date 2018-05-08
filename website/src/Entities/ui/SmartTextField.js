import React from 'react';
import TextField from "material-ui/es/TextField/TextField";
import {TableCell, TableRow} from "material-ui/es/Table"

export class SmartTextField extends React.Component {

    constructor() {
        super();
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e) {
        const {name, onChange} = this.props;
        const value = e.target.value;
        onChange(name, value);
    }

    render() {
        const {name, errorState: {[name]: {error, message}}, value} = this.props;
        return (<TextField label={name} value={value}
                           error={error} helperText={message}
                           onChange={this.handleChange}/>);
    }
}

export class SmartTextFieldRow extends React.Component {
    render() {
        const {name} = this.props;
        return (
            <TableRow>
                <TableCell>
                    {name}
                </TableCell>
                <TableCell colSpan="2">
                    <SmartTextField {...this.props}/>
                </TableCell>
            </TableRow>
        )
    }
}
