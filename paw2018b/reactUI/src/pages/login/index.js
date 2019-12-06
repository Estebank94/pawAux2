import React from 'react';
import { Link } from 'react-router-dom';
import Shake from 'react-reveal/Shake';
import Fade from 'react-reveal/Fade';
import fetchApi from '../../utils/api'
import queryString from 'query-string';

class Login extends React.Component {
  state = {
    email: '',
    password: '',
    emailError: false,
    passwordError: false,
    error: false,
    rememberMe: false,
  };


  handleChange(e) {
    const { name, value } = e.target;
    if(name !== 'rememberMe') {
      this.setState({ [name]: value, [`${name}Error`]: false });
    } else {
      this.setState({ rememberMe: !this.state.rememberMe });
    }
  }

  handleSubmit() {
    const { email, password, rememberMe } = this.state;
    if(email && password) {
      fetchApi('/patient/login','XPOST', { Jusername: email, Jpassword: password }).then(response => console.log('respone',response))
    }
    if(!email) {
      this.setState({ emailError: true })
    }
    if(!password) {
      this.setState({ passwordError: true })
    }

  }

  render() {
    const { email, password, rememberMe, emailError, passwordError, error} = this.state;
    return (
      <div className="centered body-background">
        <Shake when={emailError || passwordError }>
          <div className="login-card w-shadow" style={{ flex: 0.3}}>
            <h3 style={{marginBottom: 16 }}>Bienvenido</h3>
            <form>
              <div className="form-group">
                <label className={emailError ? 'text-danger' : ''}>Email</label>
                <input name="email" value={email} type="email" className={'form-control ' + (emailError ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder="Ingresa tu email" onChange={(e) =>this.handleChange(e)}/>
              </div>
              <div className="form-group">
                <label className={passwordError ? 'text-danger' : ''}>Contraseña</label>
                <input name="password" value={password} type="password" className={'form-control ' + (passwordError ? 'is-invalid' : '')} placeholder="Ingresa tu contraseña" onChange={(e) => this.handleChange(e)}/>
              </div>
              <div className="form-group form-check">
                <input name="rememberMe" value={rememberMe} type="checkbox" className="form-check-input" onChange={(e) => this.handleChange(e)}/>
                  <label className="form-check-label">Mantenerme conectado</label>
              </div>
              { error &&
                <Fade>
                  <div className="form-group">
                    <div class="alert alert-danger" role="alert">
                      Email o contraseña invalidos
                    </div>
                  </div>
                </Fade>
              }
              
              <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn">Iniciar Sesion</div>
            </form>
            <div style={{ marginTop: 8 }}>
              <small>¿No tenes una cuenta? <Link>Registrate</Link></small>
              <br/>
              <small><Link>Olvidé mi contraseña</Link></small>
            </div>
          </div>
        </Shake>
      </div>
    )
  }
}

export default Login;
