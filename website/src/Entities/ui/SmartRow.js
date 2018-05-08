import React from 'react';

import {TableCell, TableRow} from "material-ui";
import {DeleteButton, ViewButton} from "./Buttons";

export function SmartRow(props) {
    const {onDelete, onView, data, orders} = props;
    const cells = orders.map(name => <TableCell key={name}> {data[name]}</TableCell>);
    const {id, emailAddress, createdOn, lastModifiedOn, score = 1} = props.data;
    return (
        <React.Fragment key={id}>
            <TableRow hover>
                {cells}
                {/*<TableCell>{id}</TableCell>*/}
                {/*<TableCell>{emailAddress}</TableCell>*/}
                {/*<TableCell>{score}</TableCell>*/}
                {/*<TableCell>{createdOn}</TableCell>*/}
                {/*<TableCell>{lastModifiedOn}</TableCell>*/}
                <TableCell>
                    <ViewButton onClick={() => onView(id)}/>
                    <DeleteButton onClick={() => onDelete(id)}/>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}