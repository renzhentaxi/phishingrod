import React from 'react';
import IconButton from "material-ui/es/IconButton/IconButton";
import "./Buttons.css";
import editIcon from './edit.svg';
import deleteIcon from './delete.svg';
import viewIcon from './view.svg';
import refreshIcon from './refreshIcon.svg';
import addIcon from './addIcon.svg';
import Tooltip from "material-ui/es/Tooltip/Tooltip";

export class ActionButton extends React.Component {

    render() {
        const {icon, alt, onClick} = this.props;
        return (
            <Tooltip title={alt}>
                <IconButton onClick={onClick}>
                    <img src={icon} className="actionButton" alt={alt}/>
                </IconButton>
            </Tooltip>
        );
    }
}

export function EditButton(props) {
    return (
        <ActionButton {...props} icon={editIcon} alt="edit"/>
    )
}

export function DeleteButton(props) {
    return (
        <ActionButton {...props} icon={deleteIcon} alt="delete"/>
    )
}

export function ViewButton(props) {
    return (
        <ActionButton {...props} icon={viewIcon} alt="view"/>
    )
}

export function RefreshButton(props) {
    return (
        <ActionButton {...props} icon={refreshIcon} alt="refresh"/>
    )
}

export function AddButton(props) {
    return (
        <ActionButton {...props} icon={addIcon} alt="add"/>
    )
}