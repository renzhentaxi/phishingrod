import React from 'react';
import PropTypes from 'prop-types';
import keycode from 'keycode';
import Downshift from 'downshift';
import {withStyles} from 'material-ui/styles';
import TextField from 'material-ui/TextField';
import Paper from 'material-ui/Paper';
import {MenuItem} from 'material-ui/Menu';
import Chip from 'material-ui/Chip';

function renderInput(inputProps) {
    const {InputProps, classes, ref, ...other} = inputProps;

    return (
        <TextField
            InputProps={{
                inputRef: ref,
                classes: {
                    root: classes.inputRoot,
                },
                ...InputProps,
            }}
            {...other}
        />
    );
}

function renderSuggestion({suggestion, index, itemProps, highlightedIndex, selectedItem}) {
    const isHighlighted = highlightedIndex === index;
    const isSelected = (selectedItem || '').indexOf(suggestion) > -1;

    return (
        <MenuItem
            {...itemProps}
            key={suggestion}
            selected={isHighlighted}
            component="div"
            style={{
                fontWeight: isSelected ? 500 : 400,
            }}
        >
            {suggestion}
        </MenuItem>
    );
}

renderSuggestion.propTypes = {
    highlightedIndex: PropTypes.number,
    index: PropTypes.number,
    itemProps: PropTypes.object,
    selectedItem: PropTypes.string,
    suggestion: PropTypes.shape({label: PropTypes.string}).isRequired,
};

function getSuggestions(candidates, inputValue) {
    let count = 0;
    return candidates.filter(suggestion => {
        const keep =
            (!inputValue || suggestion.toLowerCase().indexOf(inputValue.toLowerCase()) !== -1) &&
            count < 5;

        if (keep) {
            count += 1;
        }

        return keep;
    });
}

class DownshiftMultiple extends React.Component {
    state = {
        inputValue: '',
        selectedItem: [],
    };

    handleKeyDown = event => {
        const {inputValue, selectedItem} = this.state;
        if (selectedItem.length && !inputValue.length && keycode(event) === 'backspace') {
            this.setState({
                selectedItem: selectedItem.slice(0, selectedItem.length - 1),
            });
        }
    };

    handleInputChange = event => {
        this.setState({inputValue: event.target.value});
    };

    handleChange = item => {
        let {selectedItem} = this.state;

        if (selectedItem.indexOf(item) === -1) {
            selectedItem = [...selectedItem, item];
        }

        this.setState({
            inputValue: '',
            selectedItem,
        });
    };

    handleDelete = item => () => {
        const selectedItem = [...this.state.selectedItem];
        selectedItem.splice(selectedItem.indexOf(item), 1);

        this.setState({selectedItem});
    };

    render() {
        const {classes} = this.props;
        const {inputValue, selectedItem} = this.state;

        return (
            <Downshift inputValue={inputValue} onChange={this.handleChange} selectedItem={selectedItem}>
                {({
                      getInputProps,
                      getItemProps,
                      isOpen,
                      inputValue: inputValue2,
                      selectedItem: selectedItem2,
                      highlightedIndex,
                  }) => (
                    <div className={classes.container}>
                        {renderInput({
                            fullWidth: true,
                            classes,
                            InputProps: getInputProps({
                                startAdornment: selectedItem.map(item => (
                                    <Chip
                                        key={item}
                                        tabIndex={-1}
                                        label={item}
                                        className={classes.chip}
                                        onDelete={this.handleDelete(item)}
                                    />
                                )),
                                onChange: this.handleInputChange,
                                onKeyDown: this.handleKeyDown,
                                placeholder: 'Select multiple countries',
                                id: 'integration-downshift-multiple',
                            }),
                        })}
                        {isOpen ? (
                            <Paper className={classes.paper} square>
                                {getSuggestions(inputValue2).map((suggestion, index) =>
                                    renderSuggestion({
                                        suggestion,
                                        index,
                                        itemProps: getItemProps({item: suggestion}),
                                        highlightedIndex,
                                        selectedItem: selectedItem2,
                                    }),
                                )}
                            </Paper>
                        ) : null}
                    </div>
                )}
            </Downshift>
        );
    }
}

DownshiftMultiple.propTypes = {
    classes: PropTypes.object.isRequired,
};

const styles = theme => ({
    root: {
        flexGrow: 1,
    },
    container: {
        flexGrow: 1,
        position: 'relative',
    },
    paper: {
        position: 'absolute',
        zIndex: 1,
        marginTop: theme.spacing.unit,
        left: 0,
        right: 0,
    },
    chip: {
        margin: `${theme.spacing.unit / 2}px ${theme.spacing.unit / 4}px`,
    },
    inputRoot: {
        flexWrap: 'wrap',
    },
});

function IntegrationDownshift(props) {
    const {classes, candidates} = props;

    return (
        <div className={classes.root}>
            <Downshift onChange={props.onChange}>
                {({getInputProps, getItemProps, isOpen, inputValue, selectedItem, highlightedIndex}) => (
                    <div className={classes.container}>
                        {renderInput({
                            fullWidth: true,
                            classes,
                            InputProps: getInputProps({
                                placeholder: props.name,
                            }),
                        })}
                        {isOpen ? (
                            <Paper className={classes.paper} square>
                                {getSuggestions(candidates, inputValue).map((suggestion, index) =>
                                    renderSuggestion({
                                        suggestion,
                                        index,
                                        itemProps: getItemProps({item: suggestion}),
                                        highlightedIndex,
                                        selectedItem,
                                    }),
                                )}
                            </Paper>
                        ) : null}
                    </div>
                )}
            </Downshift>
            {/*<DownshiftMultiple classes={classes} />*/}
        </div>
    );
}

IntegrationDownshift.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(IntegrationDownshift);