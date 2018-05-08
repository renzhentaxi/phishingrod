import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import PhishingTargetTable from './Entities/targets/PhishingTargetTable'
import {AppMenu} from "./AppMenu";
import SpoofTargetTable from "./Entities/targets/SpoofTargetTable";
import Switch from "react-router-dom/es/Switch";
import {Route} from "react-router-dom";

class App extends Component {
    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">Welcome to PhishingRod</h1>
                </header>
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
