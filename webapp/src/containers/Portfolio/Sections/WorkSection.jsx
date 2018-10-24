import React from "react";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// core components
import GridContainer from "../../../components/MaterialKit/Grid/GridContainer.jsx";
import GridItem from "../../../components/MaterialKit/Grid/GridItem.jsx";
import CustomInput from "../../../components/MaterialKit/CustomInput/CustomInput.jsx";
import Button from "../../../components/MaterialKit/CustomButtons/Button.jsx";

import workStyle
    from "../../../components/MaterialKit/dependencies/jss/material-kit-react/views/landingPageSections/workStyle.jsx";

class WorkSection extends React.Component {
    render() {
        const {classes} = this.props;
        return (
            <div className={classes.section}>
                <GridContainer justify="center">
                    <GridItem cs={12} sm={12} md={8}>
                        <h2 className={classes.title}>Work with me</h2>
                        <h4 className={classes.description}>
                            Send me
                        </h4>
                        <form>
                            <GridContainer>
                                <GridItem xs={12} sm={12} md={6}>
                                    <CustomInput
                                        labelText="Your Name"
                                        id="name"
                                        formControlProps={{
                                            fullWidth: true
                                        }}
                                    />
                                </GridItem>
                                <GridItem xs={12} sm={12} md={6}>
                                    <CustomInput
                                        labelText="Your Email"
                                        id="email"
                                        formControlProps={{
                                            fullWidth: true
                                        }}
                                    />
                                </GridItem>
                                <CustomInput
                                    labelText="Your Message"
                                    id="message"
                                    formControlProps={{
                                        fullWidth: true,
                                        className: classes.textArea
                                    }}
                                    inputProps={{
                                        multiline: true,
                                        rows: 5
                                    }}
                                />
                                <GridContainer justify="center">
                                    <GridItem
                                        xs={12}
                                        sm={12}
                                        md={4}
                                        className={classes.textCenter}
                                    >
                                        <Button color="primary">Send Message</Button>
                                    </GridItem>
                                </GridContainer>
                            </GridContainer>
                        </form>
                    </GridItem>
                </GridContainer>
            </div>
        );
    }
}

export default withStyles(workStyle)(WorkSection);
