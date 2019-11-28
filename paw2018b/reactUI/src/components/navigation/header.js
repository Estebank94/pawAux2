import React from 'react'
import { Link } from 'react-router-dom';

class Navigation extends React.Component {
  handleClick() {
    const { id } = this.props.data;
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
            <a href="/login">
                <div className="row">
                    <div className="center-vertical">
                      <button
                        onClick={() => this.handleClick()}
                        className="btn btn-light" style={{ backgroundColor: 'transparent', borderColor: 'transparent', color: '#257CBF' }}
                        type="button"
                      >
                        Iniciar Sesion
                      </button>
                    </div>
                </div>
            </a>
        </div>
      </nav>
    );
  }
}

export default Navigation;
