import React from "react";
import {TableCell, TableRow} from 'material-ui/Table';
import TableHead from "material-ui/es/Table/TableHead";
import Table from "material-ui/es/Table/Table";
import TableBody from "material-ui/es/Table/TableBody";
import {AddButton, RefreshButton} from "../ui/Buttons";
import PhishingTargetRow from "./PhishingTargetRow";
import axios from "axios";
import {PhishingTargetAPI} from "../API";

class PhishingTargetTable extends React.Component {

    constructor() {
        super();
        this.state = {data: []};
    }

    async loadData() {
        // const {data} = await axios.get('http://localhost:8080/api/phishingTarget');
        const {data} = await PhishingTargetAPI.all();
        this.setState({data});
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
                        <RefreshButton/>
                        <AddButton/>
                    </TableCell>

                </TableRow>
            </TableHead>
        );

        const body = this.state.data.map((t) => <PhishingTargetRow key={t.id} data={t}/>);

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