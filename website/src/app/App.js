import React, {Component} from 'react';
import './App.css';

import {AppMenu} from "./AppMenu";
import Switch from "react-router-dom/es/Switch";
import {Route} from "react-router-dom";
import {PhishingTargetTable} from "../Entities/targets/PhishingTargetTable";
import {SpoofTargetTable} from "../Entities/targets/SpoofTargetTable";

class App extends Component {

    render() {
        return (
            <div className="App">
                <AppMenu/>
                <Switch>
                    <Route exact path='/' component={PhishingTargetTable}/>
                    <Route exact path='/phishingTarget' component={PhishingTargetTable}/>
                    <Route exact path='/spoofTarget' component={SpoofTargetTable}/>
                    <Route exact path='/schedule'/>
                </Switch>
            </div>
        );
    }
}

export default App;
