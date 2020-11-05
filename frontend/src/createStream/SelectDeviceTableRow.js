import React from 'react'
import {
    Collapse,
    List,
    ListItem,
    ListItemText,
    MenuItem,
    Select,
    Typography
} from "@material-ui/core";
import {
    ExpandLess,
    ExpandMore
} from '@material-ui/icons/';

export default class SelectDeviceTableRow extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            deviceName: props.deviceDetails.name,
            channels: props.deviceDetails.channels,
            open: false
        }
    }

    render() {
        return (
            <div>
                <ListItem button dense onClick={() => this.setState({ open: !this.state.open })}>
                    <ListItemText
                        primary={this.state.deviceName}
                    />
                    {this.state.open ? <ExpandLess /> : <ExpandMore />}
                </ListItem>
                <Collapse in={this.state.open} timeout="auto" unmountOnExit >
                    <List component="div" disablePadding>
                        <ListItem  divider>
                            <ListItemText secondary="Select Channel"  />
                            <Select
                            value= "" >
                                {
                                    this.state.channels.map((channel) => {
                                        return (
                                            <MenuItem value={channel.port} key={this.state.deviceName + channel.port}>
                                                <Typography variant="caption"> {channel.port}</Typography>
                                            </MenuItem>
                                        );
                                    })
                                }
                            </Select>
                        </ListItem>
                    </List>
                </Collapse>
            </div>
        );
    }
}