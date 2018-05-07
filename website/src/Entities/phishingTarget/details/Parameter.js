import React from 'react';
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import {SmartTextField} from "../SmartTextField";
import {DeleteButton} from "../../ui/Buttons";

export class Parameter extends React.Component {
    render() {
        const {name, value, onChange, onDelete} = this.props;

        return (
            <TableRow>
                <TableCell>
                    {name}
                </TableCell>
                <TableCell>
                    <SmartTextField label={name} value={value} onChange={onChange}/>
                </TableCell>
                <TableCell><DeleteButton onClick={()=> onDelete(name)}/></TableCell>
            </TableRow>
        );
    }
}
