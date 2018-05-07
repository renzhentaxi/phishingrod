import React from "react";
import TableRow from "material-ui/es/Table/TableRow";
import TableCell from "material-ui/es/Table/TableCell";
import {DeleteButton, ViewButton} from "../ui/Buttons";
import withStyles from "material-ui/es/styles/withStyles";
import PhishingTargetDetail from "./details/PhishingTargetDetail";

const styles = theme => ({
    root: {
        width: '100%'
    },
    hide: {
        visibility: "collapse"
    }
});

class PhishingTargetRow extends React.Component {
    state = {showView: false};

    constructor() {
        super();
        this.openView = this.openView.bind(this);
        this.closeView = this.closeView.bind(this);
    }

    openView() {
        this.setState({showView: true});
    }

    closeView() {
        this.setState({showView: false});
    }


    render() {
        const {id, emailAddress, createdOn, lastModifiedOn, score = 1, parameters} = this.props.data;
        const {showView} = this.state;
        const {onDelete} = this.props;
        return (
            <React.Fragment key={id}>
                <TableRow hover>
                    <TableCell>{id}</TableCell>
                    <TableCell>{emailAddress}</TableCell>
                    <TableCell>{score}</TableCell>
                    <TableCell>{createdOn}</TableCell>
                    <TableCell>{lastModifiedOn}</TableCell>
                    <TableCell>
                        <ViewButton onClick={this.openView}/>
                        <PhishingTargetDetail data={this.props.data} open={showView} onClose={this.closeView}
                                              onChange={this.props.onChange}/>
                        <DeleteButton onClick={() => onDelete(id)}/>
                    </TableCell>
                </TableRow>
            </React.Fragment>
        );
    }
}

export default withStyles(styles)(PhishingTargetRow);