import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import PhishingTargetTable from './Entities/phishingTarget/PhishingTargetTable'

class App extends Component {
    render() {
        const sampleData = [
            {id: 1, emailAddress: "taxi@gmail.com", createdOn: "yesterday", parameters: {name: "my boi"}},
            {id: 2, emailAddress: "hi@taxi.com", createdOn: "today", parameters: {name: "taxi"}}
        ];
        return (

            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">Welcome to PhishingRod</h1>
                </header>
                <PhishingTargetTable data={sampleData}/>
            </div>
        );
    }
}

export default App;
