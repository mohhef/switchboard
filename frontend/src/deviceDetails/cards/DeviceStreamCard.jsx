import React from "react";
import PropTypes from "prop-types";
import { Grid } from "@material-ui/core";

import DeviceInfo from "../../model/DeviceInfo";

import DashboardCard from "../../general/dashboard/DashboardCard";

export default function DeviceStreamCard(props) {
  const { device } = props;

  return (
    <DashboardCard title="Streams">
      <Grid item xs={12}>
        Awaiting stream info for
        {device.name}
      </Grid>
    </DashboardCard>
  );
}

DeviceStreamCard.propTypes = {
  device: PropTypes.instanceOf(DeviceInfo).isRequired
};
