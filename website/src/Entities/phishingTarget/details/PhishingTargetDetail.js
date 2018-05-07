import React from 'react';
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
import Button from "material-ui/es/Button/Button";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import update from 'immutability-helper';
import {AddParameterRow} from "./AddParameterRow";
import {Parameter} from "./Parameter";
import {PhishingTargetAPI} from "../../API";
import {SmartTextField} from "../SmartTextField";

function TextFieldRow(props) {
    return (
        <TableRow>
            <TableCell>
                {props.label}
            </TableCell>
            <TableCell colSpan="2">
                <SmartTextField {...props}/>
            </TableCell>
        </TableRow>
    );
}

function SaveButton(props) {
    return (<TableRow>
        <TableCell colSpan="3" style={{textAlign: "center", width: "100%"}}>
            <Button onClick={props.onClick} variant="raised" color="primary"
                    style={{width: "100%"}}> Save </Button>
        </TableCell>
    </TableRow>);
}

class PhishingTargetDetail extends React.Component {

    state = {
        data: {
            parameters: {}
        }
    };

    constructor() {
        super();
        this.handleAddParameter = this.handleAddParameter.bind(this);
        this.handleChangeParameter = this.handleChangeParameter.bind(this);
        this.handleDeleteParameter = this.handleDeleteParameter.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.handleEmailChange = this.handleEmailChange.bind(this);
    }

    componentWillMount() {
        this.setState({data: this.props.data});
    }

    handleDeleteParameter(name) {
        let parameters = Object.assign({}, this.state.data.parameters);
        delete parameters[name];
        const newState = update(this.state, {
            data:
                {
                    parameters: {$set: parameters}
                }
        });
        this.setState(newState);
    }

    handleChangeParameter(name, value, onError) {

        let state = update(this.state, {
            data: {
                parameters: {
                    [name]: {$set: value}
                }
            }
        });

        this.setState(state);
        if (value.trim().length === 0) {
            onError("Value can not be empty")
        } else {
            return true;
        }
    }

    handleAddParameter(name, value, error) {
        const parameters = Object.assign({}, this.state.data.parameters);

        if (name.length === 0) {
            error("name", "Name can not be empty");
        }
        else if (parameters.hasOwnProperty(name)) {
            error("name", "Parameter already exist");
        }
        else if (value.trim().length === 0) {
            error("value", "Value can not be empty")
        }
        else {
            parameters[name] = value;
            this.setState(update(this.state, {data: {parameters: {$set: parameters}}}));
            return true;
        }
    }

    handleSave() {
        console.log(this.state.data);
        PhishingTargetAPI.modify(this.state.data.id, this.state.data);
        console.log("pressed save");
    }

    handleEmailChange(label, value, onError) {

        this.setState(update(this.state, {data: {emailAddress: {$set: value}}}));
        if (value.trim().length === 0) onError("Email Address can not be empty");
        else return true;
    }

    render() {
        const {open, onClose} = this.props;
        const {id, emailAddress, createdOn, lastModifiedOn, parameters} = this.state.data;
        const paramList = [];
        for (let p in parameters) {
            paramList.push(<Parameter key={p} name={p} value={parameters[p]}
                                      onDelete={this.handleDeleteParameter}
                                      onChange={this.handleChangeParameter}/>)
        }

        const baseFields = [
            <TextFieldRow key="Id" label="Id" value={id} disabled/>,
            <TextFieldRow key="Email" label="Email Address" value={emailAddress} onChange={this.handleEmailChange}/>,
            <TextFieldRow key="Created On" label="Created On" value={createdOn} disabled/>,
            <TextFieldRow key="Last Modified On" label="Last Modified On" value={lastModifiedOn} disabled/>
        ];


        return (
            <Dialog open={open} onClose={onClose} maxWidth="md">
                <DialogTitle style={{textAlign: "center"}}>Phishing Target: {emailAddress} </DialogTitle>
                <Table>
                    <TableBody>
                        {baseFields}
                        {paramList}
                        <AddParameterRow onAdd={this.handleAddParameter} onChange={this.handleAddParameterChange}/>
                        <SaveButton onClick={this.handleSave}/>
                    </TableBody>
                </Table>
            </Dialog>
        )
    }
}

export default PhishingTargetDetail;
