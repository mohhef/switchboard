import React from "react";
import { Box, Container } from "@material-ui/core";
import * as useStyles from "../DefaultMakeStylesTheme";
import StreamsTable from "./StreamsTable";

export default function StreamList(props) {
  return (
    <Container>
      <Box class="headerArea">
        <div className="title">Current Streams</div>
      </Box>
      <StreamsTable classes={useStyles} dataSource={props.dataSource} />
    </Container>
  );
}
