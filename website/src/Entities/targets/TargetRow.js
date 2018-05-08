import React from "react";
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import {DeleteButton, ViewButton} from "../ui/Buttons";

export function TargetRow(props) {
    const {onDelete, onView} = props;
    const {id, emailAddress, createdOn, lastModifiedOn, score = 1} = props.data;
    return (
        <React.Fragment key={id}>
            <TableRow hover>
                <TableCell>{id}</TableCell>
                <TableCell>{emailAddress}</TableCell>
                <TableCell>{score}</TableCell>
                <TableCell>{createdOn}</TableCell>
                <TableCell>{lastModifiedOn}</TableCell>
                <TableCell>
                    <ViewButton onClick={() => onView(id)}/>
                    <DeleteButton onClick={() => onDelete(id)}/>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}

