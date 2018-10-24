import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
// nodejs library to set properties for components
import PropTypes from "prop-types";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// core components
import cardStyle from "../dependencies/jss/material-kit-react/components/cardStyle.jsx";

// @material-ui/icons

function Card({...props}) {
    const {classes, className, children, plain, carousel, ...rest} = props;
    const cardClasses = classNames({
        [classes.card]: true,
        [classes.cardPlain]: plain,
        [classes.cardCarousel]: carousel,
        [className]: className !== undefined
    });
    return (
        <div className={cardClasses} {...rest}>
            {children}
        </div>
    );
}

Card.propTypes = {
    classes: PropTypes.object.isRequired,
    className: PropTypes.string,
    plain: PropTypes.bool,
    carousel: PropTypes.bool
};

export default withStyles(cardStyle)(Card);