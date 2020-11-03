import React from 'react';
import {
    Box,
    Container,
    makeStyles  
} from "@material-ui/core";
import useStyles from '../DefaultMakeStylesTheme';

export default function StreamList() {
    const styles = useStyles();

    return (
        <Container>
            <Box class="headerArea">
                <div class="title">
                    Current Streams
                </div>
            </Box>

        </Container>
    );
}
