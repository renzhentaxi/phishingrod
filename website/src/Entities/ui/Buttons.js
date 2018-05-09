import React from 'react';
import IconButton from "material-ui/es/IconButton/IconButton";
import "./Buttons.css";
import editIcon from './icons/edit.svg';
import deleteIcon from './icons/delete.svg';
import viewIcon from './icons/view.svg';
import refreshIcon from './icons/refreshIcon.svg';
import addIcon from './icons/addIcon.svg';
import Tooltip from "material-ui/es/Tooltip/Tooltip";

export class ActionButton extends React.Component {

    render() {
        const {icon, alt, onClick, disabled = false, style} = this.props;
        return (
            <Tooltip title={alt}>
                <IconButton onClick={onClick} disabled={disabled}>
                    <img src={icon} className="actionButton" alt={alt} style={style}/>
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

