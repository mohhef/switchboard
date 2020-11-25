import React from "react";
import { Box, Container } from "@material-ui/core";
import PropTypes from "prop-types";

import StreamingTable from "./StreamingTable";
import DynamicBreadcrumb from "../General/DynamicBreadcrumb";
import StreamList from "../streamlist/StreamList";
import DeviceInfo from "../model/DeviceInfo";
import StreamInfo from "../model/StreamInfo";

export default class StreamingPage extends React.Component {
  constructor(props) {
    super(props);
    this.dataSource = props.deviceDataSource;
  }

  render() {
    return (
      <Container>
        <DynamicBreadcrumb
          breadcrumbs={[
            ["Home", ""],
            ["My Devices", "Devices"],
            ["Streaming", "Streaming"]
          ]}
        />
        <Box padding="1em">
          <Box className="flexContents headerArea">
            <div className="title">Streaming</div>
          </Box>
          <div id="StreamingTable">
            <StreamingTable dataSource={this.props.deviceDataSource} />
          </div>
        </Box>
        <Box>
          <StreamList dataSource={this.props.streamDataSource} />
        </Box>
      </Container>
    );
  }
}

DeviceDetailsConciseTable.propTypes = {
  deviceDataSource: PropTypes.arrayOf(PropTypes.instanceOf(DeviceInfo))
    .isRequired,
  streamDataSource: PropTypes.arrayOf(PropTypes.instanceOf(StreamInfo))
    .isRequired
};
