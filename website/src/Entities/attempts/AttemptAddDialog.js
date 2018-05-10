import React from 'react';
import {attachDataState} from "../../util/ErrorHandler";
import {bind} from "../../util/util";
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import HintTextArea from "./Searchbox"
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import Button from "material-ui/es/Button/Button";

const FIELDS = ["phishingTarget", "emailTemplate", "spoofTarget", "sender"];


const DEFAULT_STATE = attachDataState({}, FIELDS);

export class AttemptAddDialog extends React.Component {
    state = Object.assign({}, DEFAULT_STATE);

    constructor() {
        super();
        bind(this, "handle", "reset")
    }

    reset() {
        this.setState(DEFAULT_STATE);
    }

    validators = {};
    validatorList = Object.values(this.validators);

    altState = {
        emailTemplate: "",
        phishingTarget: "",
        spoofTarget: "",
        sender: ""
    };

    handle(type, value) {
        switch (type) {
            case "template":
                this.altState.emailTemplate = value;
                break;
            case "phish":
                this.altState.phishingTarget = value;
                break;
            case "spoof":
                this.altState.spoofTarget = value;
                break;
            case "sender":
                this.altState.sender = value;
                break;
            case "close":
                this.props.onSave(this.altState);
                this.props.onClose();
                break;
        }
    }

    render() {
        const {open, onClose} = this.props;
        const {hasError, errors} = this.state;
        return (<Dialog open={open} onClose={onClose} maxWidth="md">
            <DialogTitle style={{textAlign: "center"}}>Schedule Attempt</DialogTitle>
            <Table>
                <TableBody>
                    <TableRow>
                        <TableCell>
                            Email Template:
                        </TableCell>
                        <TableCell>
                            <HintTextArea onChange={this.handle.bind(this, "template")}
                                          candidates={this.props.candidates.templates}
                                          name={"Email Templates"}/>
                        </TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>
                            Phishing Target:
                        </TableCell>
                        <TableCell>
                            <HintTextArea onChange={this.handle.bind(this, "phish")}
                                          candidates={this.props.candidates.phishingTargets}
                                          name={"Phishing Target"}/>
                        </TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>
                            Spoof Target:
                        </TableCell>
                        <TableCell>
                            <HintTextArea onChange={this.handle.bind(this, "spoof")}
                                          candidates={this.props.candidates.spoofTargets}
                                          name={"Spoof Target"}/>
                        </TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>Sender
                        </TableCell>
                        <TableCell>
                            <HintTextArea onChange={this.handle.bind(this, "sender")}
                                          candidates={this.props.candidates.senders}
                                          name={"Senders"}/>
                        </TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell colSpan={2} style={{width: "100%"}}><Button style={{width: "100%"}} color="primary"
                                                                               variant="raised"
                                                                               onClick={() => this.handle("close")}>Schedule</Button></TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </Dialog>);
    }
}

// export class SenderAddDialog extends React.Component {
//
//     constructor() {
//         super();
//         this.state = Object.assign({}, DEFAULT_STATE);
//
//         this.reset = this.reset.bind(this);
//         this.handleChange = this.handleChange.bind(this);
//         this.handleClose = this.handleClose.bind(this);
//         this.handleAdd = this.handleAdd.bind(this);
//     }
//
//     validators =
//         {
//             name: createValidator(this, "name", notEmptyValidator),
//             password: createValidator(this, "password", notEmptyValidator),
//             server: createValidator(this, "server", notEmptyValidator),
//         };
//
//
//     handleChange(name, value) {
//         smartSetState(this, DataUpdater(name, value), this.validators[name]);
//     }
//
//     handleAdd() {
//         const {data} = this.state;
//         if (smartValidate(this, ...this.validatorList)) {
//             this.props.onAdd(data);
//             this.props.onClose();
//             this.reset();
//         }
//     }
//
//     handleClose() {
//         this.props.onClose();
//         this.reset();
//     }
//
//     reset() {
//         this.setState(DEFAULT_STATE);
//     }
//
//     render() {
//         const {open} = this.props;
//         const {hasError, errors} = this.state;
//         return (
//             <Dialog open={open} onClose={this.handleClose} maxWidth="md">
//                 <DialogTitle style={{textAlign: "center"}}>Add Dialog</DialogTitle>
//                 <Table>
//                     <TableBody>
//                         {
//                             FIELDS.map(name => <SmartTextFieldRow key={name} name={name}
//                                                                   onChange={this.handleChange}
//                                                                   errorState={errors}/>)
//                         }
//                         <ButtonRow onClick={this.handleAdd} text="add" disabled={hasError}/>
//                     </TableBody>
//                 </Table>
//             </Dialog>
//         )
//     }
// }
