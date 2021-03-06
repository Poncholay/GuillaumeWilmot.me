import React from 'react';
import ReactDOM from 'react-dom';
import './scss/Index.scss';
import {App} from './App';
import * as serviceWorker from './serviceWorker';

import {history, store} from "./_helpers";
import CssBaseline from "@material-ui/core/CssBaseline/CssBaseline";
import {Router} from "react-router-dom";
import {Provider} from "react-redux";

import "./components/MaterialKit/dependencies/scss/material-kit-react.css?v=1.3.0";

ReactDOM.render((
        <Provider store={store}>
            <Router history={history}>
                <CssBaseline>
                    <App/>
                </CssBaseline>
            </Router>
        </Provider>
    ), document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
