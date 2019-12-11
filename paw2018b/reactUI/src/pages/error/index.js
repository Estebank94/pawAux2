import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFrownOpen } from '@fortawesome/free-solid-svg-icons';

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
              <p>Hubo un problema</p>
              <Link className="btn btn-primary custom-btn" to="/">Ir a la pagina principal</Link>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Error;
