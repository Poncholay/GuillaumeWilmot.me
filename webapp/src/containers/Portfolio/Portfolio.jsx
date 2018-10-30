import React from "react";
import classNames from "classnames";
import withStyles from "@material-ui/core/styles/withStyles";
import Footer from "../../components/MaterialKit/Footer/Footer.jsx";
import GridContainer from "../../components/MaterialKit/Grid/GridContainer.jsx";
import GridItem from "../../components/MaterialKit/Grid/GridItem.jsx";
import Button from "../../components/MaterialKit/CustomButtons/Button.jsx";
import Parallax from "../../components/MaterialKit/Parallax/Parallax.jsx";
import ProductSection from "./Sections/ProductSection.jsx";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import AppHeader from "../AppHeader/AppHeader";
import PortfolioStyle from "../../scss/PortfolioStyle";

class Portfolio extends React.Component {
    render() {
        const {classes} = this.props;
        return (
            <div>
                <AppHeader title={"Portfolio"}/>
                <Parallax filter image={require("../../assets/background.jpg")}>
                    <div className={classes.container}>
                        <GridContainer>
                            <GridItem xs={12} sm={12} md={6}>
                                <h1 className={classes.title}>Developer and code lover.</h1>
                                <h4>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent at pellentesque
                                    nisi, non malesuada justo. Vestibulum ante ipsum primis in faucibus orci luctus et
                                    ultrices posuere cubilia Curae; Donec euismod augue eu magna volutpat, in vulputate
                                    felis mattis. Quisque id erat ac nulla consequat porta.
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

const connectedPortfolio = withRouter(connect(mapStateToProps)(withStyles(PortfolioStyle)(Portfolio)));

export {connectedPortfolio as Portfolio};
