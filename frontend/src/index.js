import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import HeaderBar from './HeaderAppBar';
import DeviceList from './devicelist/DeviceList';
import StreamingTable from './createStream/StreamingPage';

import {
  Breadcrumbs,
  Container,
  Typography,
  Link,
  Box,
} from "@material-ui/core"

ReactDOM.render(
  <React.StrictMode>
    <HeaderBar />
    <Container>
      <Box padding="2em 0em 0em 0em">
        <Breadcrumbs aria-label="breadcrumb">
          <Link color="inherit" href="/" >
            Home
        </Link>
          <Typography component={'span'} color="textPrimary">Devices</Typography>
        </Breadcrumbs>
      </Box>
    </Container>
    <Box padding="1em">
      <StreamingTable/>
       <DeviceList />
    </Box>
  </React.StrictMode>,
  document.getElementById('root')
);
