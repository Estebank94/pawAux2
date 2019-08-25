import React from 'react'
import { Link } from 'react-router-dom';

const Navigation = props => {
  return(
    <nav class="navbar navbar-dark" style={{ backgroundColor: '#FFFFFF', paddingBottom: 0 }}>
      <div class="container">
          <a class="navbar-brand" href="/">
              <h1 class="navbar-brand-home"><strong>Waldoc</strong></h1>
          </a>
          <a href="/login">
              <div class="row">
                  <div class="center-vertical">
                    <button class="btn btn-light" style={{ backgroundColor: 'transparent', borderColor: 'transparent', color: '#257CBF' }} type="button">
                      Iniciar Sesion
                    </button>
                  </div>
              </div>
          </a>
      </div>
    </nav>
  );
}

export default Navigation
