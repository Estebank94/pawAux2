import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFrownOpen } from '@fortawesome/free-solid-svg-icons';
import i18n from "../../i18n";

class Error extends React.Component {
  state = {
    error: ''
  }

  componentDidMount() {
    const { error } = this.props.match.params;
    this.setState({ error });
  }

  render() {
    const { error } = this.state;
    return (
      <div className="body-background">
        <div className="container col-12-sm w-p-20">
          <div className="login-card w-shadow">
            <div>
              <FontAwesomeIcon icon={faFrownOpen} color="#257CBF" size="4x"/>
              <h3 className="mt-4">{error}</h3>
              <p>{i18n.t('error.problem')}</p>
              <Link className="btn btn-primary custom-btn" to="/">{i18n.t('error.toHome')}</Link>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Error;
