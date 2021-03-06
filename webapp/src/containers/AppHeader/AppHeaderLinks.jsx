import React from "react";
import {Link} from "react-router-dom";
import withStyles from "@material-ui/core/styles/withStyles";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import {AccountCircle, Apps} from "@material-ui/icons";
import CustomDropdown from "../../components/MaterialKit/CustomDropdown/CustomDropdown.jsx";
import Button from "../../components/MaterialKit/CustomButtons/Button.jsx";

import headerLinksStyle
    from "../../components/MaterialKit/dependencies/jss/material-kit-react/components/headerLinksStyle.jsx";

function AppHeaderLinks({...props}) {
    const {classes} = props;
    return (
        <List className={classes.list}>
            <ListItem className={classes.listItem}>
                <CustomDropdown
                    noLiPadding
                    buttonText="Applications"
                    buttonProps={{
                        className: classes.navLink,
                        color: "transparent"
                    }}
                    buttonIcon={Apps}
                    dropdownList={[
                        <Link to="/portfolio" className={classes.dropdownLink}>
                            Portfolio
                        </Link>,
                        <Link to="/doievenlift" className={classes.dropdownLink}>
                            Powerlifting app
                        </Link>
                    ]}
                />
            </ListItem>
            <ListItem className={classes.listItem}>
                <Button
                    href="#pablo"
                    className={classes.navLink}
                    onClick={e => e.preventDefault()}
                    color="transparent"
                >
                    <AccountCircle className={classes.icons}/> Profile
                </Button>
            </ListItem>
        </List>
    );
}

export default withStyles(headerLinksStyle)(AppHeaderLinks);
