import React, {Component} from 'react';
import {connect} from 'react-redux';
import {config} from "./_helpers/index";
import {history} from './_helpers/index';
import NoMatch from "./containers/NoMatch";
import {Route, Switch, withRouter} from 'react-router-dom';
import PropTypes from 'prop-types';
import {Portfolio} from "./containers/Portfolio/Portfolio";
import {Powerlifting} from "./containers/Powerlifting/Powerlifting";
import {Redirect} from "react-router";
import withStyles from "@material-ui/core/styles/withStyles";
import AppStyle from "./scss/AppStyle";

class App extends Component {

    constructor(props) {
        super(props);

        history.listen((location, action) => {
            // clear alert on location change
            // window.ga('send', 'pageview', location.pathname);
        });
    }

    render() {
        return (
            <section className="content">
                <Switch>
                    <Route exact path={`${config.PORTFOLIO}`} component={Portfolio}/>
                    <Route exact path={`${config.POWERLIFTING}`} component={Powerlifting}/>
                    <Route exact path={`${config.BASE_URL}`} component={() => <Redirect to={`${config.PORTFOLIO}`} />}/>
                    <Route component={NoMatch}/>
                </Switch>
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

const connectedApp = withRouter(connect(mapStateToProps)(withStyles(AppStyle)(App)));
export {connectedApp as App};