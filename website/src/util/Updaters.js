import update from 'immutability-helper';

export function DataUpdater(name, value) {
    return state => update(state, {data: {[name]: {$set: value}}})
}