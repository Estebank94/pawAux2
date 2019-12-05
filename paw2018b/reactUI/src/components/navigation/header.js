import React from 'react'
import { Link } from 'react-router-dom';
import { DropdownButton, Dropdown } from 'react-bootstrap';
import SearchBar from '../home/searchBar';

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
              <Dropdown.Item href="/register/patient">Registrarse como paciente</Dropdown.Item>
              <Dropdown.Item href="/register/specialist">Registrarse como especialista</Dropdown.Item>
            </DropdownButton>
            <div className="center-vertical">
              <button
                onClick={() => this.handleClick()}
                className="btn btn-light ml-2" style={{ backgroundColor: 'transparent', borderColor: 'transparent', color: pathname === '/' ? '#257CBF' : '#FFF' }}
                type="button"
              >
                Iniciar Sesion
              </button>
            </div>
          </div>
        </div>
        {
          pathname === '/specialists' &&
          <div className="container">
            <div className="col-sm-12 pl-0 pr-0 pb-2">
              <SearchBar dark/>
            </div>
          </div>


        }
      </nav>
    );
  }
}

export default Navigation;
