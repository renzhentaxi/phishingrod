import React from 'react';
import TextField from "material-ui/es/TextField/TextField";

function upperCase(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

export class SmartTextField extends React.Component {

    constructor() {
        super();
        this.state = {error: false, message: ""};
        this.handleChange = this.handleChange.bind(this);
        this.handleError = this.handleError.bind(this);
    }

    handleChange(e) {
        const value = e.target.value;
        const hasChanged = this.props.onChange(this.props.label, value, this.handleError);
        if (hasChanged) {
            this.clearError();
        }
    }


    handleError(message) {
        this.setState({error: true, message});
    }

    clearError() {
        this.setState({error: false, message: ""})
    }

    render() {
        const {label, value} = this.props;
        const {error, message} = this.state;
        return (<TextField label={upperCase(label)} value={value}
                           error={error} helperText={message}
                           onChange={this.handleChange}/>);
    }
}

