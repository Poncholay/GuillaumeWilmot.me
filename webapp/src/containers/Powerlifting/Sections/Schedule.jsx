import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import ScheduleStyle from "../../../scss/ScheduleStyle";

class Schedule extends React.Component {
    render() {
        const {classes} = this.props;
        return (
            <div className={classes.section}>

            </div>
        );
    }
}

export default withStyles(ScheduleStyle)(Schedule);
