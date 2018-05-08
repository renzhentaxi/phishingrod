import React from 'react';
import logo from './logo.svg';

export function AppHeader(props) {
    return (
        <header className="App-header" style={{backgroundColor: props.color}}>
            <img src={logo} className="App-logo" alt="logo"/>
            <h1 className="App-title">{props.title}</h1>
        </header>);
}