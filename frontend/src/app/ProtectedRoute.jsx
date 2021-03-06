import React from "react";
import PropTypes from "prop-types";
import { Redirect, Route } from "react-router-dom";

import { isAuthenticated } from "../api/AuthenticationUtil";

export default class ProtectedRoute extends React.Component {
  constructor(props) {
    super(props);
    this.component = this.component.bind(this);
  }

  component(location) {
    const { authenticationRequired, render } = this.props;
    const authenticated = isAuthenticated();

    if (authenticationRequired) {
      if (authenticated) {
        return render(location);
      }

      return <Redirect to={{ pathname: "/Login" }} />;
    }

    if (authenticated) {
      return <Redirect to={{ pathname: "/Home" }} />;
    }

    return render(location);
  }

  render() {
    const { path } = this.props;
    return (
      <Route
        exact
        path={path}
        render={({ location }) => this.component(location)}
      />
    );
  }
}

ProtectedRoute.propTypes = {
  path: PropTypes.string.isRequired,
  authenticationRequired: PropTypes.bool,
  render: PropTypes.func.isRequired
};

ProtectedRoute.defaultProps = {
  authenticationRequired: false
};
