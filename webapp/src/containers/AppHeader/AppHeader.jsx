import Header from "../../components/MaterialKit/Header/Header";
import React from "react";
import PortfolioHeaderLinks from "./AppHeaderLinks";
import * as _ from "underscore";
import {withStyles} from "@material-ui/core";
import AppHeaderStyle from "../../scss/AppHeaderStyle";

class AppHeader extends React.Component {
    state = {
        height: window.innerHeight
    };

    componentWillMount() {
        window.addEventListener('resize', _.debounce(this.handleWindowSizeChange, 100));
    }

    componentWillUnmount() {
        window.removeEventListener('resize', this.handleWindowSizeChange);
    }

    componentDidMount() {
        this.handleWindowSizeChange();
    }

    handleWindowSizeChange = () => {
        this.setState({
            height: window.innerHeight
        });
    };

    render() {
        const {height} = this.state;
        const {title} = this.props;
        const transformHeight = height / (height <= 600 ? 5 : height > 1080 ? 4 : 3);

        return (
            <Header
                color="transparent"
                brand={title}
                rightLinks={<PortfolioHeaderLinks/>}
                fixed
                changeColorOnScroll={{
                    height: transformHeight,
                    color: "white"
                }}
            />);
    }
}

export default withStyles(AppHeaderStyle)(AppHeader);