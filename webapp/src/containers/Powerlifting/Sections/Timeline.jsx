import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import TimelineStyle from "../../../scss/TimelineStyle";

class Timeline extends React.Component {
    render() {
        const {classes} = this.props;
        return (
            <div className={classes.section}>
                I think that’s a responsibility that I have, to push possibilities, to show people, this is the level that things could be at. So when you get something that has the name Kanye West on it, it’s supposed to be pushing the furthest possibilities. I will be the leader of a company that ends up being worth billions of dollars, because I got the answers. I understand culture. I am the nucleus
                <br/>
                I think that’s a responsibility that I have, to push possibilities, to show people, this is the level that things could be at. So when you get something that has the name Kanye West on it, it’s supposed to be pushing the furthest possibilities. I will be the leader of a company that ends up being worth billions of dollars, because I got the answers. I understand culture. I am the nucleus
                <br/>
                I think that’s a responsibility that I have, to push possibilities, to show people, this is the level that things could be at. So when you get something that has the name Kanye West on it, it’s supposed to be pushing the furthest possibilities. I will be the leader of a company that ends up being worth billions of dollars, because I got the answers. I understand culture. I am the nucleus
            </div>
        );
    }
}

export default withStyles(TimelineStyle)(Timeline);
