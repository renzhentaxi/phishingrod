import React from "react";
import {TableCell, TableRow} from 'material-ui/Table';
import TableHead from "material-ui/es/Table/TableHead";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import {AddButton, RefreshButton} from "../ui/Buttons";
import {SenderServerAPI} from "../API";
import update from "immutability-helper";
import {TargetEditDialog} from "../targets/details/TargetEditDialog";
import {AppHeader} from "../../app/AppHeader";
import {SenderServerAddDialog} from "./dialog/SenderServerAddDialog";
import {SmartRow} from "../ui/SmartRow";

export class SenderServerTable extends React.Component {

    api = SenderServerAPI;
    state = {
        data: [],
        showAddDialog: false,
        showEditDialog: false,
    };

    constructor() {
        super();
        this.editRef = React.createRef();
        this.handleDelete = this.handleDelete.bind(this);
        this.refreshData = this.refreshData.bind(this);

        this.handleAdd = this.handleAdd.bind(this);
        this.openAddDialog = this.openAddDialog.bind(this);
        this.closeAddDialog = this.closeAddDialog.bind(this);

        this.handleSave = this.handleSave.bind(this);
        this.openEditDialog = this.openEditDialog.bind(this);
        this.closeEditDialog = this.closeEditDialog.bind(this);
    }

    componentWillMount() {
        this.refreshData();
    }

    async refreshData() {
        const {data} = await this.api.all();
        this.setState({data});
    }

    async handleDelete(id) {
        await this.api.delete(id);
        this.refreshData();
    }

    async handleAdd(target) {
        await this.api.add(target);
        this.refreshData();
    }

    async handleSave(target) {
        await this.api.modify(target.id, target);
        this.refreshData();
    }

    openAddDialog() {
        const newState = update(this.state, {showAddDialog: {$set: true}});
        this.setState(newState);
    }

    openEditDialog(id) {
        const target = this.state.data.find(t => t.id === id);
        this.editRef.current.initalize(target);
        this.setState((state) => {
            return update(state, {showEditDialog: {$set: true}});
        });
    }

    closeAddDialog() {
        this.setState(update(this.state, {showAddDialog: {$set: false}}))
    }

    closeEditDialog(id) {
        this.setState(update(this.state, {showEditDialog: {$set: false}}))
    }

    renderHead() {
        return (
            <TableHead>
                <TableRow>
                    <TableCell>Id</TableCell>
                    <TableCell>Name</TableCell>
                    <TableCell>Host</TableCell>
                    <TableCell>Port</TableCell>
                    <TableCell>Score</TableCell>
                    <TableCell>
                        <RefreshButton onClick={this.refreshData}/>
                        <AddButton onClick={this.openAddDialog}/>
                        <SenderServerAddDialog open={this.state.showAddDialog}
                                               onClose={this.closeAddDialog}
                                               onAdd={this.handleAdd}/>
                        <TargetEditDialog open={this.state.showEditDialog}
                                          ref={this.editRef}
                                          onClose={this.closeEditDialog}
                                          onSave={this.handleSave}/>
                    </TableCell>

                </TableRow>
            </TableHead>
        );
    }

    orders = ["id", "name", "host", "port", "score"];

    renderBody() {
        return (<TableBody>
            {this.state.data.map((t) => <SmartRow key={t.id} data={t}
                                                  orders={this.orders}
                                                  onView={this.openEditDialog}
                                                  onDelete={this.handleDelete}/>)}
        </TableBody>);
    }

    render() {
        return (
            <div>
                <AppHeader title="Sender Servers" color="firebrick"/>
                <Table>
                    {this.renderHead()}
                    {this.renderBody()}
                </Table>
            </div>
        );
    }
}
