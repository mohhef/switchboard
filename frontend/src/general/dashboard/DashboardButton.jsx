import React from "react";
import PropTypes from "prop-types";
import "./dashboard.css";
import { NavLink } from "react-router-dom";
import { Button, withStyles } from "@material-ui/core";

export default function DashboardButton(props) {
  const { href, children, passedState } = props;

  const StyledButton = withStyles({
    root: {
      background: "linear-gradient(45deg, #59bc31 30%, #59bc31 90%)",
      borderRadius: 90,
      border: 0,
      color: "white",
      height: 40,
      minWidth: 100,
      maxWidth: 200,
      padding: "0 5px",
      textTransform: "none"
    }
  })(Button);
  return (
    <NavLink
      activeClassName="hideLinkStyle"
      className="hideLinkStyle"
      color="inherit"
      to={{
        pathname: href,
        state: { passedState }
      }}
    >
      <StyledButton>{children}</StyledButton>
    </NavLink>
  );
}

DashboardButton.defaultProps = {
  passedState: 0
};
DashboardButton.propTypes = {
  href: PropTypes.string.isRequired,
  children: PropTypes.oneOfType([
    PropTypes.string,
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node
  ]).isRequired,
  passedState: PropTypes.number
};
