import React from 'react';
import { Link } from 'react-router-dom';
import Shake from 'react-reveal/Shake';
import Fade from 'react-reveal/Fade';
import { connect } from 'react-redux';
import { doLogin } from '../../store/auth/actions';
import i18n from "../../i18n";
import PulseLoader from 'react-spinners/PulseLoader';

class Login extends React.Component {
  state = {
    email: '',
    password: '',
    emailError: false,
    passwordError: false,
    error: false,
    loading: false
  };


  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value, [`${name}Error`]: false, error: false });
  }

  async handleSubmit() {
    const { email, password } = this.state;
    if(email && password) {
      this.setState({ loading: true })
      await this.props.doLogin({ Jusername: email, Jpassword: password }).then(auth => this.manageAccess(auth));
      this.setState({ loading: false })
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
      if(auth.user.complete) {
        this.props.history.push('/complete');
      } else {
        this.props.history.push('/');
      }
    }
  }

  render() {
    const { email, password, emailError, passwordError, error, loading } = this.state;
    return (
      <div className="centered-login body-background">
        <Shake when={emailError || passwordError || error }>
          <div className="login-card w-shadow" style={{ flex: 0.3}}>
            <h3 style={{marginBottom: 16 }}>{i18n.t('login.welcome')}</h3>
            <form>
              <div className="form-group">
                <label className={emailError ? 'text-danger' : ''}>{i18n.t('login.email')}</label>
                <input name="email" value={email} type="email" className={'form-control ' + (emailError ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder={i18n.t('login.placeHolderEmail')} onChange={(e) =>this.handleChange(e)}/>
              </div>
              <div className="form-group">
                <label className={passwordError ? 'text-danger' : ''}>{i18n.t('login.password')}</label>
                <input name="password" value={password} type="password" className={'form-control ' + (passwordError ? 'is-invalid' : '')} placeholder={i18n.t('login.placeHolderasPsword')} onChange={(e) => this.handleChange(e)}/>
              </div>
              { error &&
                <Fade>
                  <div className="form-group">
                    <div className="alert alert-danger" role="alert">
                        {i18n.t('login.error')}
                    </div>
                  </div>
                </Fade>
              }
              {
                loading ?
                  <div className="btn btn-primary custom-btn btn-block">
                    <PulseLoader
                      sizeUnit={"px"}
                      size={10}
                      color={'#FFF'}
                      loading={true}
                    />
                  </div>
                  :
                  <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn btn-block">{i18n.t('navigation.logInButton')}</div>
              }
            </form>
            <div style={{ marginTop: 8 }}>
              <small>{i18n.t('login.accountQuestion')} <Link to="/register">{i18n.t('login.register')}</Link></small>
              <br/>
              <small><Link to="/">{i18n.t('login.cancel')}</Link></small>
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
