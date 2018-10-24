import React from "react";
import classNames from "classnames";
import withStyles from "@material-ui/core/styles/withStyles";
import Footer from "../../components/MaterialKit/Footer/Footer.jsx";
import GridContainer from "../../components/MaterialKit/Grid/GridContainer.jsx";
import GridItem from "../../components/MaterialKit/Grid/GridItem.jsx";
import Parallax from "../../components/MaterialKit/Parallax/Parallax.jsx";
import landingPageStyle from "../../components/MaterialKit/dependencies/jss/material-kit-react/views/landingPage.jsx";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import AppHeader from "../Portfolio/AppHeader/AppHeader";
import WorkSection from "../Portfolio/Sections/WorkSection";

class Powerlifting extends React.Component {
    render() {
        const {classes} = this.props;
        return (
            <div>
                <AppHeader title={"Powerlifting App"}/>
                <Parallax filter image={require("../../assets/background.jpg")}>
                    <div className={classes.container}>
                        <GridContainer>
                            <GridItem xs={12} sm={12} md={6}>
                                <h1 className={classes.title}>Powerlifting app.</h1>
                                <h4>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent at pellentesque
                                    nisi, non malesuada justo.
                                </h4>
                                <br/>
                            </GridItem>
                        </GridContainer>
                    </div>
                </Parallax>
                <div className={classNames(classes.main, classes.mainRaised)}>
                    <div className={classes.container}>
                        <WorkSection/>
                    </div>
                </div>
                <Footer/>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {};
}

const connectedPowerlifting = withRouter(connect(mapStateToProps)(withStyles(landingPageStyle)(Powerlifting)));

export {connectedPowerlifting as Powerlifting};
