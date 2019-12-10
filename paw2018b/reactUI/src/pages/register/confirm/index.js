import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons';


class Confirm extends React.Component {
  render() {
    return (
      <div className="body-background">
        <div className="container col-12-sm w-p-20">
          <div className="login-card w-shadow">
            <div>
              <FontAwesomeIcon icon={faCheckCircle} color="#46ce23" size="4x"/>
              <h3 className="mt-4">Cuenta activada</h3>
              <p>Ya podes acceder a tu cuenta.</p>
              <Link className="btn btn-primary custom-btn" to="/">Ir a la pagina principal</Link>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Confirm;
