import React from 'react';
import ReactDOM from 'react-dom';
import './scss/Index.scss';
import {App} from './App';
import * as serviceWorker from './serviceWorker';

import {history, store, style} from "./_helpers";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import CssBaseline from "@material-ui/core/CssBaseline/CssBaseline";
import {Router} from "react-router-dom";
import {Provider} from "react-redux";

import "./components/MaterialKit/dependencies/scss/material-kit-react.css?v=1.3.0";

const theme = createMuiTheme({
    palette: {
        primary: {
            main: style.colors.primary,
        },
        secondary: {
            main: style.colors.secondary,
        },
        accent1Color: "#89BC31",
        pickerHeaderColor: "#000000"
    },
});

ReactDOM.render((
        <Provider store={store}>
            <Router history={history}>
                <MuiThemeProvider theme={theme}>
                    <CssBaseline/>
                    <App/>
                </MuiThemeProvider>
            </Router>
        </Provider>
    ), document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
