import React from "react";
import classNames from "classnames";
import withStyles from "@material-ui/core/styles/withStyles";
import Header from "../../components/MaterialKit/Header/Header.jsx";
import Footer from "../../components/MaterialKit/Footer/Footer.jsx";
import GridContainer from "../../components/MaterialKit/Grid/GridContainer.jsx";
import GridItem from "../../components/MaterialKit/Grid/GridItem.jsx";
import Button from "../../components/MaterialKit/CustomButtons/Button.jsx";
import HeaderLinks from "../../components/MaterialKit/Header/HeaderLinks.jsx";
import Parallax from "../../components/MaterialKit/Parallax/Parallax.jsx";
import landingPageStyle from "../../components/MaterialKit/dependencies/jss/material-kit-react/views/landingPage.jsx";
import ProductSection from "./Sections/ProductSection.jsx";
import {withRouter} from "react-router";
import {connect} from "react-redux";

const dashboardRoutes = [];

class LandingPage extends React.Component {
    render() {
        const {classes, ...rest} = this.props;
        return (
            <div>
                <Header
                    color="transparent"
                    routes={dashboardRoutes}
                    brand="Guillaume Wilmot"
                    rightLinks={<HeaderLinks/>}
                    fixed
                    changeColorOnScroll={{
                        height: 400,
                        color: "white"
                    }}
                    {...rest}
                />
                <Parallax filter image={require("../../assets/background.jpeg")}>
                    <div className={classes.container}>
                        <GridContainer>
                            <GridItem xs={12} sm={12} md={6}>
                                <h1 className={classes.title}>Developer by passion.</h1>
                                <h4>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent at pellentesque nisi, non malesuada justo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec euismod augue eu magna volutpat, in vulputate felis mattis. Quisque id erat ac nulla consequat porta.
                                </h4>
                                <br/>
                                <Button
                                    color="danger"
                                    size="lg"
                                    href="https://www.youtube.com/watch?v=dQw4w9WgXcQ"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                >
                                    <i className="fas fa-play"/>Watch video
                                </Button>
                            </GridItem>
                        </GridContainer>
                    </div>
                </Parallax>
                <div className={classNames(classes.main, classes.mainRaised)}>
                    <div className={classes.container}>
                        <ProductSection/>
                        {/*<WorkSection />*/}
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

const connectedLandingPage = withRouter(connect(mapStateToProps)(withStyles(landingPageStyle)(LandingPage)));

export {connectedLandingPage as LandingPage};
