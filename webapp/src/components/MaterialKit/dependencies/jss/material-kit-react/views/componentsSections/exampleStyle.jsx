import {containerFluid} from "../../../material-kit-react.jsx";

import imagesStyle from "../../imagesStyles.jsx";

const exampleStyle = {
    section: {
        padding: "70px 0"
    },
    container: {
        ...containerFluid,
        textAlign: "center !important"
    },
    ...imagesStyle,
    link: {
        textDecoration: "none"
    }
};

export default exampleStyle;
