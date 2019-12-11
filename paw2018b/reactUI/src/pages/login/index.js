import React from 'react';
import { Link } from 'react-router-dom';
import Shake from 'react-reveal/Shake';
import Fade from 'react-reveal/Fade';
import { connect } from 'react-redux';
import { doLogin } from '../../store/auth/actions';

class Login extends React.Component {
  state = {
    email: '',
    password: '',
    emailError: false,
    passwordError: false,
    error: false,
  };


  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value, [`${name}Error`]: false });
  }

  async handleSubmit() {
    const { email, password } = this.state;
    if(email && password) {
      await this.props.doLogin({ Jusername: email, Jpassword: password }).then(auth => this.manageAccess(auth));
    }
    if(!email) {
      this.setState({ emailError: true })
    }
    if(!password) {
      this.setState({ passwordError: true })
    }
  }

  manageAccess = async (auth) => {
    if (auth.status === 'failed') {
      this.setState({ error: true });
    } else if (auth.status === 'authenticated') {
      this.props.history.push('/');
    }
  }

  render() {
    const { email, password, emailError, passwordError, error} = this.state;
    return (
      <div className="centered body-background">
        <Shake when={emailError || passwordError || error }>
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
              { error &&
                <Fade>
                  <div className="form-group">
                    <div className="alert alert-danger" role="alert">
                      Email o contraseña invalidos
                    </div>
                  </div>
                </Fade>
              }
              
              <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn">Iniciar Sesion</div>
            </form>
            <div style={{ marginTop: 8 }}>
              <small>¿No tenes una cuenta? <Link to="/register">Registrate</Link></small>
              <br/>
              <small><Link to="/">Cancelar</Link></small>
            </div>
          </div>
        </Shake>
      </div>
    )
  }
}

function bindActions(dispatch) {
  return {
    doLogin: (credentials) => dispatch(doLogin(credentials)),
  };
}

export default connect(null, bindActions)(Login);
