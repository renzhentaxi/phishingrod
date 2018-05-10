import React from 'react';
import {Table} from "material-ui";
import {AppHeader} from "../../app/AppHeader";
import TableHead from "material-ui/es/Table/TableHead";
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import {AddButton, RefreshButton} from "../ui/Buttons";
import {AttemptAPI, EmailTemplateAPI, PhishingTargetAPI, SenderAPI, SpoofTargetAPI} from "../API";
import update from 'immutability-helper';
import {AttemptAddDialog} from "./AttemptAddDialog";
import {bind} from "../../util/util";
import TableBody from "material-ui/es/Table/TableBody";
import Button from "material-ui/es/Button/Button";
import Dialog from "material-ui/es/Dialog/Dialog";
import DialogTitle from "material-ui/es/Dialog/DialogTitle";

const config = {
    title: "Attempts",
    color: "orange",
    columns: ["Id", "Phishing Target", "Email Template", "Spoof Target", "Sender", "Status"]
};

const HeaderRow = (props) => (<TableHead>
    <TableRow>
        {props.columns.map(name => <TableCell key={name}>{name}</TableCell>)}
        <TableCell>
            <RefreshButton onClick={() => props.handle("refresh")}/>
            <AddButton onClick={() => props.handle("openAddDialog")}/>
            <AttemptAddDialog open={props.open} onClose={() => props.handle("closeAddDialog")}
                              candidates={props.candidates}
                              onSave={(data) => props.handle("add", data)}/>
        </TableCell>
    </TableRow>
</TableHead>);

function convertToIdentifiers(entities, identifierName) {
    return entities.data.map(e => e[identifierName]);
}

export class AttemptTable extends React.Component {
    state = {
        data: [],
        hint: {
            templates: [],
            phishingTargets: [],
            spoofTargets: [],
            senders: []
        },
        openAddDialog: false,
        viewTime: false
    };

    constructor() {
        super();
        bind(this, "refreshData", "handle");
    }

    componentWillMount() {
        this.refreshData();
    }

    async refreshData() {
        const templates = convertToIdentifiers(await
                EmailTemplateAPI.all(), "name"
            )
        ;
        const phishingTargets = convertToIdentifiers(await
                PhishingTargetAPI.all(), "emailAddress"
            )
        ;
        const spoofTargets = convertToIdentifiers(await
                SpoofTargetAPI.all(), "emailAddress"
            )
        ;
        const senders = convertToIdentifiers(await
                SenderAPI.all(), "name"
            )
        ;
        const attempts = await AttemptAPI.all();
        const hint = {templates, phishingTargets, spoofTargets, senders};
        this.setState((state) => update(state, {
            data: {$set: attempts.data},
            hint: {$set: hint}
        }));
    }

    handle(type, data) {
        switch (type) {
            case "openAddDialog":
                this.setState({openAddDialog: true});
                break;
            case "closeAddDialog":
                this.setState({openAddDialog: false});
                break;
            case "add":
                AttemptAPI.post("/schedule", data);
                this.refreshData();
                break;
            case "refresh":
                this.refreshData();
                break;
            case "send":
                AttemptAPI.post("/" + data + "/execute");
                this.refreshData();
                break;
            case "viewTime":
                this.setState(({viewTime: true}));
                break;
            case "closeTime":
                this.setState({viewTime: false});
                break;
        }
    }

    render() {
        const {title, color, columns} = config;
        const {data, openAddDialog} = this.state;
        console.log(data);
        return (
            <div>
                <AppHeader title={title} color={color}/>
                <Table>
                    <HeaderRow columns={columns} handle={this.handle} open={openAddDialog}
                               candidates={this.state.hint}/>
                    <TableBody>
                        {
                            data.map(attempt =>
                                (<TableRow key={attempt.id}>
                                    <TableCell>{attempt.id}</TableCell>
                                    <TableCell>{attempt.phishingTarget.emailAddress}</TableCell>
                                    <TableCell>{attempt.template.name}</TableCell>
                                    <TableCell>{attempt.spoofTarget.emailAddress}</TableCell>
                                    <TableCell>{attempt.sender.name}</TableCell>
                                    <TableCell>{attempt.stage}</TableCell>
                                    <TableCell>{(attempt.stage === "scheduled") ?
                                        (<Button onClick={() => this.handle("send", attempt.id)} color="primary"
                                                 variant="raised">send</Button>) :
                                        (<React.Fragment>
                                                <Button onClick={() => this.handle("viewTime")} color="primary"
                                                        variant="raised"> View Time</Button>
                                                <Dialog open={this.state.viewTime}
                                                        onClose={() => this.handle("closeTime")}>
                                                    <DialogTitle>Time</DialogTitle>
                                                    <Table>
                                                        <TableBody>
                                                            <TableRow>
                                                                <TableCell>Sent on</TableCell>
                                                                <TableCell>{attempt.sendOn || "Not Yet"}</TableCell>
                                                            </TableRow>
                                                            <TableRow>
                                                                <TableCell>Opened on</TableCell>
                                                                <TableCell>{attempt.openedOn || "Not Yet"}</TableCell>
                                                            </TableRow>
                                                            <TableRow>
                                                                <TableCell>Phished on</TableCell>
                                                                <TableCell>{attempt.trickedOn || "Not Yet"}</TableCell>
                                                            </TableRow>
                                                        </TableBody>
                                                    </Table>
                                                </Dialog>
                                            </React.Fragment>
                                        )}</TableCell>
                                </TableRow>)
                            )
                        }
                    </TableBody>
                </Table>
            </div>
        );
    }
}