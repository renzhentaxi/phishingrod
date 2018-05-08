import React from 'react';
import TextField from "material-ui/es/TextField/TextField";
import {TableCell, TableRow} from "material-ui/es/Table"

function upperCase(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

export class SmartTextField extends React.Component {

    constructor() {
        super();
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(e) {
        const {label, onChange} = this.props;
        const value = e.target.value;
        onChange(label, value);
    }

    render() {
        const {label, errors = [], onChange, ...rest} = this.props;
        const {error, message} = (errors.find((e) => e && e.error) || {error: false, message: ""});


        return (<TextField error={error} helperText={message} label={upperCase(label)}
                           onChange={this.handleChange} {...rest}/>);
    }
}

export class SmartTextFieldRow extends React.Component {
    render() {
        const {label} = this.props;
        return (
            <TableRow>
                <TableCell>
                    {label}
                </TableCell>
                <TableCell colSpan="2">
                    <SmartTextField {...this.props}/>
                </TableCell>
            </TableRow>
        )
    }
}
