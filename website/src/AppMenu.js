import React from 'react';
import {Divider, Drawer, List, ListItem, ListItemText} from "material-ui";
import {Link} from "react-router-dom";

export function AppMenu(props) {
    return (<Drawer variant="permanent" anchor="left">
        <List>
            <ListItem>
                <ListItemText primary="Menu"/>
            </ListItem>
            <Divider/>

            <Link to="/attempts">
                <ListItem button>
                    <ListItemText primary="Attempts"/>
                </ListItem>
            </Link>

            <Link to="/phishingTarget">
                <ListItem button>
                    <ListItemText primary="Phishing Targets"/>
                </ListItem>
            </Link>

            <Link to="/spoofTarget">
                <ListItem button>
                    <ListItemText primary="Spoof Targets"/>
                </ListItem>
            </Link>

        </List>
    </Drawer>);
}