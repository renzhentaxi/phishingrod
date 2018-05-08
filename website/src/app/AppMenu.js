import React from 'react';
import {Divider, Drawer, List, ListItem, ListItemText} from "material-ui";
import {Link} from "react-router-dom";
import logo from './logo.svg'

export function AppMenu(props) {
    return (<Drawer variant="permanent" anchor="left">
        <List disablePadding>
            <ListItem style={{backgroundColor: "gray"}}>
                <img src={logo} className="App-logo" alt="logo"/>
            </ListItem>
            <Divider/>

            <Link to="/attempts">
                <ListItem button>
                    <ListItemText primary="Attempts"/>
                </ListItem>
            </Link>

            <Link to="/templates">
                <ListItem button>
                    <ListItemText primary="Templates"/>
                </ListItem>
            </Link>

            <Link to="/sender">
                <ListItem button>
                    <ListItemText primary="Senders"/>
                </ListItem>
            </Link>

            <Link to="/senderServer">
                <ListItem button>
                    <ListItemText primary="SenderServers"/>
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