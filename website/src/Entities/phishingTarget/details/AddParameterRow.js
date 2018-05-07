import React from 'react';
import TableRow from "material-ui/es/Table/TableRow";
import {SmartTextField} from "../SmartTextField";
import TableCell from "material-ui/es/Table/TableCell";
import {AddButton} from "../../ui/Buttons";


export class AddParameterRow extends React.Component {
    state = {
        name: "",
        value: ""
    };

    constructor() {
        super();
        this.nameRef = React.createRef();
        this.valueRef = React.createRef();
        this.handleChange = this.handleChange.bind(this);
        this.handleAdd = this.handleAdd.bind(this);
        this.handleError = this.handleError.bind(this);
    }

    handleChange(label, value) {
        this.setState({[label]: value});
        return true;
    }

    handleAdd() {
        const {name, value} = this.state;
        const added = this.props.onAdd(name, value, this.handleError);

        if (added) {
            this.setState({name: "", value: ""})
        }

    }

    handleError(source, message) {
        const ref = (source === "name") ? this.nameRef : this.valueRef;
        ref.current.handleError(message);
    }

    render() {
        const {name, value} = this.state;
        const {onAdd} = this.props;
        return (
            <TableRow>
                <TableCell>
                    <SmartTextField ref={this.nameRef} label="name" value={name} onChange={this.handleChange}/>
                </TableCell>
                <TableCell>
                    <SmartTextField ref={this.valueRef} label="value" value={value} onChange={this.handleChange}/>
                </TableCell>
                <TableCell>
                    <AddButton onClick={this.handleAdd}/>
                </TableCell>
            </TableRow>
        )
    }
}