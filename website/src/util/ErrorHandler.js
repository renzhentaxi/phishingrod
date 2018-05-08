//checks if the object contains any errors
import update from 'immutability-helper';

export function hasError(errors) {
    for (const p of Object.keys(errors)) {
        const errorState = errors[p];
        if (errorState.error && !errorState.ignore)
            return true;
    }
    return false;
}

function createErrorUpdater(type, error, message) {
    return (state) => {
        const newErrorsChange = {[type]: {error: {$set: error}, message: {$set: message}}};
        const newErrors = update(state.errors, newErrorsChange);
        const newHasError = error || hasError(newErrors);

        return update(state, {
            hasError: {$set: newHasError},
            errors: {$set: newErrors}
        });
    };
}

export function smartSetState(context, newState, ...validators) {
    context.setState(newState, () => smartValidate(context, ...validators))
}

export function smartValidate(context, ...validators) {
    const result = validators.map(validator => {
            const {type, message = ""} = validator();
            const valid = message === "";
            const updater = createErrorUpdater(type, !valid, message);
            context.setState(updater);
            return valid;
        }
    );
    for (const r of result) if (!r) return false;
    return true;
}

export function attachErrorState(state, ...errorTypes) {
    state.hasError = false;
    state.errors = {};
    for (const name of errorTypes) {
        state.errors[name] = {error: false, message: ""}
    }
    return state;
}

export function attachDataState(state, ...dataTypes) {
    state.data = {};
    for (const name of dataTypes) {
        state.data[name] = "";
    }
    return attachErrorState(state, ...dataTypes);
}

export function AddErrorUpdater(errorType) {
    return (state) => update(state, {errors: {[errorType]: {$set: {error: false, message: ""}}}})
}

export function DeleteErrorUpdater(errorType) {
    return (state) => update(state, {errors: {$unset: [errorType]}})
}

export function notEmptyValidator(name, value) {

    if (value.toString().trim().length === 0) return `${name} can not be empty`;
}

export function isNumberValidator(name, value) {
    if (isNaN(value)) return `${name} has to be a number`
}

export function createValidator(context, name, ...validators) {
    return () => {
        const result = {type: name};
        const {[name]: value} = context.state.data;
        for (const validator of validators) {
            const message = validator(name, value);
            if (message) {
                result.message = message;
                return result;
            }
        }
        return result;
    };
}