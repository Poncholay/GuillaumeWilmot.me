import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import Card from "../../../components/MaterialKit/Card/Card";
import GridItem from "../../../components/MaterialKit/Grid/GridItem";
import GridContainer from "../../../components/MaterialKit/Grid/GridContainer";
import moment from "moment";
import Badge from "../../../components/MaterialKit/Badge/Badge";
import CardBody from "../../../components/MaterialKit/Card/CardBody";
import PersonalRecordsStyle from "../../../scss/PersonalRecordsStyle";

class PersonalRecords extends React.Component {
    tmpLifts = [
        {
            exercise: "Squat",
            weight: 120.0,
            date: moment().format("MMMM Do YYYY")
        },
        {
            exercise: "Overhead press",
            weight: 62.5,
            date: moment().format("MMMM Do YYYY")
        },
        {
            exercise: "Deadlift",
            weight: 145.0,
            date: moment().format("MMMM Do YYYY")
        },
        {
            exercise: "Bench press",
            weight: 85.0,
            date: moment().format("MMMM Do YYYY")
        },
        {
            exercise: "Front squat",
            weight: 95,
            date: moment().format("MMMM Do YYYY")
        }
    ];

    render() {
        const {classes} = this.props;
        return (
            <div className={classes.section}>
                <GridContainer>
                    {this.tmpLifts.map(lift => {
                        return (
                            <GridItem xs={12} sm={12} md={6} lg={3}>
                                <Card>
                                    <h4 className={classes.cardTitle}>
                                        {lift.exercise}
                                        <br/>
                                        <Badge color="success">Intermediate</Badge>
                                    </h4>
                                    <CardBody>
                                        {lift.weight}
                                        <br/>
                                        {lift.date}
                                    </CardBody>
                                </Card>
                            </GridItem>
                        );
                    })}
                </GridContainer>
            </div>
        );
    }
}

export default withStyles(PersonalRecordsStyle)(PersonalRecords);
