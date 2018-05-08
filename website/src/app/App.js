import React, {Component} from 'react';
import './App.css';

import {AppMenu} from "./AppMenu";
import Switch from "react-router-dom/es/Switch";
import {Route} from "react-router-dom";
import {PhishingTargetTable} from "../Entities/targets/PhishingTargetTable";
import {SpoofTargetTable} from "../Entities/targets/SpoofTargetTable";
import {SenderServerTable} from "../Entities/senderServer/SenderServerTable";
import {SenderTable} from "../Entities/senders/SenderTable";

class App extends Component {

    render() {
        return (
            <div className="App">
                <div className="prMenu">
                    <AppMenu/>
                </div>
                <div className="prPage">
                    <Switch>
                        <Route exact path='/' component={PhishingTargetTable}/>
                        <Route exact path='/phishingTarget' component={PhishingTargetTable}/>
                        <Route exact path='/spoofTarget' component={SpoofTargetTable}/>
                        <Route exact path='/senderServer' component={SenderServerTable}/>
                        <Route exact path='/sender' component={SenderTable}/>
                    </Switch>
                </div>
            </div>
        );
    }
}

export default App;
