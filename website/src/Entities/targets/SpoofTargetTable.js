import React from "react";
import {TableCell, TableRow} from 'material-ui/Table';
import TableHead from "material-ui/es/Table/TableHead";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import {AddButton, RefreshButton} from "../ui/Buttons";
import {TargetRow} from "./TargetRow";
import {SpoofTargetAPI} from "../API";
import update from "immutability-helper";
import {TargetAddDialog} from "./details/TargetAddDialog";
import {TargetEditDialog} from "./details/TargetEditDialog";
import {AppHeader} from "../../app/AppHeader";

export class SpoofTargetTable extends React.Component {

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
        const {data} = await SpoofTargetAPI.all();
        this.setState({data});
    }

    async handleDelete(id) {
        await SpoofTargetAPI.delete(id);
        this.refreshData();
    }

    async handleAdd(target) {
        await SpoofTargetAPI.add(target);
        this.refreshData();
    }

    async handleSave(target) {
        await SpoofTargetAPI.modify(target.id, target);
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
                    <TableCell>Email Addresss</TableCell>
                    <TableCell>Created On</TableCell>
                    <TableCell>Last Modified On</TableCell>
                    <TableCell>
                        <RefreshButton onClick={this.refreshData}/>
                        <AddButton onClick={this.openAddDialog}/>
                        <TargetAddDialog open={this.state.showAddDialog}
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

    renderBody() {
        return (<TableBody>
            {this.state.data.map((t) => <TargetRow key={t.id} data={t}
                                                   onView={this.openEditDialog}
                                                   onDelete={this.handleDelete}/>)}
        </TableBody>);
    }

    render() {
        return (
            <div>
                <AppHeader title="Spoof Targets" color="lightcoral"/>
                <Table>
                    {this.renderHead()}
                    {this.renderBody()}
                </Table>
            </div>
        );
    }
}
