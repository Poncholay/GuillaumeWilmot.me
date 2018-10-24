import Header from "../../../components/MaterialKit/Header/Header";
import React from "react";
import PortfolioHeaderLinks from "./AppHeaderLinks";

class AppHeader extends React.Component {
    render() {
        const {title} = this.props;
        return (
            <Header
                color="transparent"
                brand={title}
                rightLinks={<PortfolioHeaderLinks/>}
                fixed
                changeColorOnScroll={{
                    height: 400,
                    color: "white"
                }}
            />);
    }
}

export default AppHeader;
