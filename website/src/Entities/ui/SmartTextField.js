import React from 'react';
import TextField from "material-ui/es/TextField/TextField";
import {TableCell, TableRow} from "material-ui/es/Table"
import {upperCase} from "../../util/util";

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
        const {name, errorState: {[name]: {error, message}}, value, line} = this.props;
        const multiline = line && true;
        return (<TextField
            fullWidth
            label={name} value={value}
            error={error} helperText={message}
            multiline={multiline}
            rows={line}
            onChange={this.handleChange}
            style={multiline ? {minWidth: 800} : undefined}
        />);
    }
}

export class SmartTextFieldRow extends React.Component {
    render() {
        const {name} = this.props;
        return (
            <TableRow>
                <TableCell>
                    {upperCase(name)}
                </TableCell>
                <TableCell>
                    <SmartTextField {...this.props}/>
                </TableCell>
            </TableRow>
        );
    }
}
