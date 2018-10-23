import React, {Component} from 'react';
import {connect} from 'react-redux';
import {config} from "./_helpers/index";
import {history} from './_helpers/index';
import './scss/App.scss';
import NoMatch from "./containers/NoMatch";
import {Route, Switch, withRouter} from 'react-router-dom';
import PropTypes from 'prop-types';
import {LandingPage} from "./containers/LandingPage/LandingPage";

class App extends Component {

    constructor(props) {
        super(props);

        history.listen((location, action) => {
            // clear alert on location change
            window.ga('send', 'pageview', location.pathname);
        });
    }

    render() {
        console.log("Teub");
        return (
            <section className="content">
                <Switch>
                    <Route exact path={`${config.BASE_URL}`} component={LandingPage}/>
                    <Route component={NoMatch}/>
                </ Switch>
            </section>
        );
    }
}

function mapStateToProps(state) {
    return {};
}

App.contextTypes = {
    store: PropTypes.object
};

App.propTypes = {
    notifications: PropTypes.array
};

const connectedApp = withRouter(connect(mapStateToProps)(App));
export {connectedApp as App};