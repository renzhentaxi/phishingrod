import React from "react";
import {TableCell, TableRow} from 'material-ui/Table';
import TableHead from "material-ui/es/Table/TableHead";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import {AddButton, RefreshButton} from "../ui/Buttons";
import PhishingTargetRow from "./PhishingTargetRow";
import {PhishingTargetAPI} from "../API";
import update from "immutability-helper";
import PhishingTargetAddDialog from "./details/PhishingTargetAddDialog";

class PhishingTargetTable extends React.Component {

    state =
        {
            data: [],
            showAddDialog: false
        };

    constructor() {
        super();
        this.handleDelete = this.handleDelete.bind(this);
        this.refreshData = this.refreshData.bind(this);
        this.openAddDialog = this.openAddDialog.bind(this);
        this.handleAdd = this.handleAdd.bind(this);
        this.closeAddDialog = this.closeAddDialog.bind(this);
    }

    componentWillMount() {
        this.refreshData();
    }

    async refreshData() {
        const {data} = await PhishingTargetAPI.all();
        this.setState({data});
    }

    async handleDelete(id) {
        await PhishingTargetAPI.delete(id);
        this.refreshData();
    }

    async handleAdd(target) {
        await PhishingTargetAPI.add(target);
        this.refreshData();
    }

    openAddDialog() {
        const newState = update(this.state, {showAddDialog: {$set: true}});
        this.setState(newState);
    }

    closeAddDialog() {
        this.setState(update(this.state, {showAddDialog: {$set: false}}))
    }

    renderHead() {
        return (
            <TableHead>
                <TableRow>
                    <TableCell>Id</TableCell>
                    <TableCell>Email Addresss</TableCell>
                    <TableCell>Score</TableCell>
                    <TableCell>Created On</TableCell>
                    <TableCell>Last Modified On</TableCell>
                    <TableCell>
                        <RefreshButton onClick={this.refreshData}/>
                        <AddButton onClick={this.openAddDialog}/>
                        <PhishingTargetAddDialog open={this.state.showAddDialog}
                                                 onClose={this.closeAddDialog}
                                                 onAdd={this.handleAdd}/>
                    </TableCell>

                </TableRow>
            </TableHead>
        );
    }

    renderBody() {
        return (<TableBody>
            {this.state.data.map((t) => <PhishingTargetRow key={t.id} data={t} onChange={this.refreshData}
                                                           onDelete={this.handleDelete}/>)}
        </TableBody>);
    }

    render() {
        const head = this.renderHead();
        const body = this.renderBody();
        return (
            <Table>
                {this.renderHead()}
                {this.renderBody()}
            </Table>
        );
    }
}

export default PhishingTargetTable;