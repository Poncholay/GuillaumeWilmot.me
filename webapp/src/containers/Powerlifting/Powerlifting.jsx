import React from "react";
import classNames from "classnames";
import withStyles from "@material-ui/core/styles/withStyles";
import Footer from "../../components/MaterialKit/Footer/Footer.jsx";
import GridContainer from "../../components/MaterialKit/Grid/GridContainer.jsx";
import GridItem from "../../components/MaterialKit/Grid/GridItem.jsx";
import Parallax from "../../components/MaterialKit/Parallax/Parallax.jsx";
import {withRouter} from "react-router";
import {connect} from "react-redux";
import CustomTabs from "../../components/MaterialKit/CustomTabs/CustomTabs";
import Timeline from "./Sections/Timeline";
import Schedule from "./Sections/Schedule";
import {Schedule as ScheduleIcon, Grade as GradeIcon, Assignment as AssignmentIcon} from "@material-ui/icons";
import BarbellIcon from "../../components/Icons/Barbell";
import AppHeader from "../AppHeader/AppHeader";
import PersonalRecords from "./Sections/PersonalRecords";
import PowerliftingStyle from "../../scss/PowerliftingStyle";

class Powerlifting extends React.Component {
    render() {
        const {classes} = this.props;
        return (
            <div>
                <AppHeader title={"Powerlifting journal"}/>
                <Parallax filter image={require("../../assets/background.jpg")}>
                    <div className={classes.container}>
                        <GridContainer>
                            <GridItem xs={12} sm={12} md={6}>
                                <h1 className={classes.title}>Powerlifting journal.</h1>
                                <h4>
                                    For when pen and paper just doesn't sound right.
                                </h4>
                                <br/>
                            </GridItem>
                        </GridContainer>
                    </div>
                </Parallax>
                <div className={classNames(classes.main, classes.mainRaised, classes.noSideMargin)}>
                    <CustomTabs
                        headerColor="primary"
                        tabs={[
                            {
                                tabName: "Timeline",
                                tabIcon: BarbellIcon,
                                tabContent: (
                                    <Timeline/>
                                )
                            },
                            {
                                tabName: "Schedule",
                                tabIcon: ScheduleIcon,
                                tabContent: (
                                    <Schedule/>
                                )
                            },
                            {
                                tabName: "PRs",
                                tabIcon: GradeIcon,
                                tabContent: (
                                    <PersonalRecords/>
                                )
                            },
                            {
                                tabName: "Goals",
                                tabIcon: AssignmentIcon,
                                tabContent: (
                                    <PersonalRecords/>
                                )
                            }
                        ]}
                    />
                </div>
                <Footer/>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {};
}

const connectedPowerlifting = withRouter(connect(mapStateToProps)(withStyles(PowerliftingStyle)(Powerlifting)));

export {connectedPowerlifting as Powerlifting};
