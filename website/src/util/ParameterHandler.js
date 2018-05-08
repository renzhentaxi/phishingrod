import update from 'immutability-helper';

export function SetParameterUpdater(name, value) {
    return (state) => update(state, {data: {parameters: {[name]: {$set: value}}}})
}

export function DeleteParameterUpdater(name) {
    return (state) => update(state, {data: {parameters: {$unset: [name]}}})
}