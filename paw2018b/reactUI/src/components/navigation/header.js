import React from 'react'
import { doSignOut } from '../../store/auth/actions'
import { connect } from 'react-redux';
import { DropdownButton, Dropdown } from 'react-bootstrap';
import SearchBar from '../home/searchBar';

class Navigation extends React.Component {
  handleClick() {
    this.props.history.push(`/login`);
  }

  signOut() {
    this.props.doSignOut();
    this.props.history.push(`/`);
  }

  render() {
    const { pathname } = this.props.location;
    const { user } = this.props.user;
    return(
      <nav className="navbar navbar-dark" style={{ backgroundColor: pathname === '/' ? '#FFFFFF' : '#257CBF', paddingBottom: 0 }}>
        <div className="container">
            <a className="navbar-brand" href="/">
                <h1 className={pathname === '/' ? 'navbar-brand-home' : ''}><strong>Waldoc</strong></h1>
            </a>
          {
            !user ?
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
              :
              <div>
                <div className="row">
                  <DropdownButton id="dropdown-basic-button btn-register" variant="light" title={user.firstName + ' ' + user.lastName}>
                    <Dropdown.Item onClick={() => this.props.history.push('/account')}>Mi Cuenta</Dropdown.Item>
                    <Dropdown.Item onClick={() => this.signOut()}>Cerrar Sesión</Dropdown.Item>
                  </DropdownButton>
                </div>
              </div>
          }
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

const mapStateToProps = state => ({
  user: state.auth,
});

function bindActions(dispatch) {
  return {
    doSignOut: () => dispatch(doSignOut()),
  };
}

export default connect(mapStateToProps, bindActions)(Navigation);

