import {createStore} from 'redux';
import rootReducer from '../reduxTest/reducers/index';

const store = createStore(rootReducer);
export default store;