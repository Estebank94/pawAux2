import React from 'react'
import { doSignout } from '../../store/auth/actions'
import { connect } from 'react-redux';
import { DropdownButton, Dropdown } from 'react-bootstrap';
import SearchBar from '../home/searchBar';
import i18n from "../../i18n";
import PropTypes from 'prop-types';

class Navigation extends React.Component {
  static propTypes = {
    history: PropTypes.object.isRequired,
    location: PropTypes.object.isRequired,
    user: PropTypes.object,
    doSignout: PropTypes.func

  };

  handleClick() {
    this.props.history.push(`/login`);
  }

  signOut() {
    this.props.doSignout();
    this.props.history.push(`/`);
  }

  render() {
    const { pathname } = this.props.location;
    const { user } = this.props.user;
    return(
      <nav className="navbar navbar-dark" style={{ backgroundColor: pathname === '/' ? '#FFFFFF' : '#257CBF', paddingBottom: 0 }}>
        <div className="container">
            <a className="navbar-brand" href="/">
                <h1 className={pathname === '/' ? 'navbar-brand-home' : ''}><strong>{i18n.t('navigation.title')}</strong></h1>
            </a>
          {
            !user ?
              <div className="row">
                <DropdownButton id="dropdown-basic-button btn-register" variant="light" title={i18n.t('navigation.register')}>
                  <Dropdown.Item href="/register/patient">{i18n.t('navigation.patientRegistrationTitle')}</Dropdown.Item>
                  <Dropdown.Item href="/register/specialist">{i18n.t('navigation.doctorRegistrationTitle')}</Dropdown.Item>
                </DropdownButton>
                <div className="center-vertical">
                  <button
                    onClick={() => this.handleClick()}
                    className="btn btn-light ml-2" style={{ backgroundColor: 'transparent', borderColor: 'transparent', color: pathname === '/' ? '#257CBF' : '#FFF' }}
                    type="button">
                      {i18n.t('navigation.logInButton')}
                  </button>
                </div>
              </div>
              :
              <div style={{paddingRight: 10}}>
                <div className="row">
                  <DropdownButton id="dropdown-basic-button btn-register" variant="light" title={user.firstName + ' ' + user.lastName}>
                    <Dropdown.Item onClick={() => this.props.history.push('/account')}>{i18n.t('navigation.myAccountTitle')}</Dropdown.Item>
                    <Dropdown.Item onClick={() => this.signOut()}>{i18n.t('navigation.logOutButton')}</Dropdown.Item>
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
    doSignout: () => dispatch(doSignout()),
  };
}

export default connect(mapStateToProps, bindActions)(Navigation);

