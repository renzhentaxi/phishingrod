import React from 'react';

import {TableCell, TableRow} from "material-ui";
import {DeleteButton, EditButton} from "./Buttons";

export function SmartRow(props) {
    const {onDelete, onEdit, data, orders} = props;
    const cells = orders.map(name => <TableCell key={name}> {data[name]}</TableCell>);
    const {id} = props.data;
    return (
        <TableRow hover>
            {cells}
            <TableCell>
                <EditButton onClick={() => onEdit(id)}/>
                <DeleteButton onClick={() => onDelete(id)}/>
            </TableCell>
        </TableRow>
    );
}

export function BasicRow(props) {
    const cells = props.data.map(field => field ? <TableCell key={field}>{field}</TableCell> : null);
    return (
        <TableRow>
            {cells}
        </TableRow>
    );
}