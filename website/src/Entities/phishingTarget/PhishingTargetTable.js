import React from "react";
import {TableCell, TableRow} from 'material-ui/Table';
import TableHead from "material-ui/es/Table/TableHead";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import {AddButton, RefreshButton} from "../ui/Buttons";
import PhishingTargetRow from "./PhishingTargetRow";
import {PhishingTargetAPI} from "../API";

class PhishingTargetTable extends React.Component {

    constructor() {
        super();
        this.state = {data: []};
        this.handleDelete = this.handleDelete.bind(this);
        this.loadData = this.loadData.bind(this);
    }

    async loadData() {
        const {data} = await PhishingTargetAPI.all();
        this.setState({data});
    }

    async handleDelete(id) {
        await PhishingTargetAPI.delete(id);
        this.loadData();
    }

    componentWillMount() {
        this.loadData();
    }

    render() {
        const head = (
            <TableHead>
                <TableRow>
                    <TableCell>Id</TableCell>
                    <TableCell>Email Addresss</TableCell>
                    <TableCell>Score</TableCell>
                    <TableCell>Created On</TableCell>
                    <TableCell>Last Modified On</TableCell>
                    <TableCell>
                        <RefreshButton onClick={this.loadData}/>
                        <AddButton/>
                    </TableCell>

                </TableRow>
            </TableHead>
        );

        const body = this.state.data.map((t) => <PhishingTargetRow key={t.id} data={t} onChange={this.loadData}
                                                                   onDelete={this.handleDelete}/>);
        return (
            <Table>
                {head}
                <TableBody>
                    {body}
                </TableBody>
            </Table>
        );
    }
}

export default PhishingTargetTable;