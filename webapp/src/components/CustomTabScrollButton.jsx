import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import classNames from 'classnames';
import TabScrollButton from "@material-ui/core/Tabs/TabScrollButton";
import CustomTabScrollButtonStyle from "../scss/CustomTabScrollButtonStyle";

function CustomTabScrollButton(props) {
    const {classes} = props;
    const newProps = {
        ...props,
        className: classNames(props.className, classes.tabScrollButton),
        classes: undefined //Do not pass unknown classes to child
    };
    return (
        <TabScrollButton {...newProps}/>
    );
}

export default withStyles(CustomTabScrollButtonStyle)(CustomTabScrollButton);
