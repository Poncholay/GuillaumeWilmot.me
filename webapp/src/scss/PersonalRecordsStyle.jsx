import {cardTitle, title} from "../components/MaterialKit/dependencies/jss/material-kit-react";

const PersonalRecordsStyle = {
    section: {
        padding: "70px 0",
        textAlign: "center"
    },
    title: {
        ...title,
        marginBottom: "1rem",
        marginTop: "30px",
        minHeight: "32px",
        textDecoration: "none"
    },
    itemGrid: {
        marginLeft: "auto",
        marginRight: "auto"
    },
    cardTitle,
    smallTitle: {
        color: "#6c757d"
    },
    description: {
        color: "#999"
    }
};

export default PersonalRecordsStyle;
