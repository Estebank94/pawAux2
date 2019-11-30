import React from 'react'
import { Link } from 'react-router-dom';
import { DropdownButton, Dropdown } from 'react-bootstrap';

class Navigation extends React.Component {
  handleClick() {
    this.props.history.push(`/login`);
  }

  render() {
    const { pathname } = this.props.location;
    return(
      <nav className="navbar navbar-dark" style={{ backgroundColor: pathname === '/' ? '#FFFFFF' : '#257CBF', paddingBottom: 0 }}>
        <div className="container">
            <a className="navbar-brand" href="/">
                <h1 className={pathname === '/' ? 'navbar-brand-home' : ''}><strong>Waldoc</strong></h1>
            </a>
          <div className="row">
            <DropdownButton id="dropdown-basic-button btn-register" variant="light" title="Registrarse">
              <Dropdown.Item href="/register">Registrarse como paciente</Dropdown.Item>
              <Dropdown.Item href="#/action-2">Registrarse como especialista</Dropdown.Item>
            </DropdownButton>
            <div className="center-vertical">
              <button
                onClick={() => this.handleClick()}
                className="btn btn-light" style={{ backgroundColor: 'transparent', borderColor: 'transparent', color: pathname === '/' ? '#257CBF' : '#FFF' }}
                type="button"
              >
                Iniciar Sesion
              </button>
            </div>
          </div>
        </div>
      </nav>
    );
  }
}

export default Navigation;
