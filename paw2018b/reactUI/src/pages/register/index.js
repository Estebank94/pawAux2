import React from 'react';
import { Link } from 'react-router-dom';
import { isValidEmail, isValidPhone } from '../../utils/validations';
import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';
import Steps, { Step } from 'rc-steps';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoffee, faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import { ApiClient } from '../../utils/apiClient';
import i18n from "../../i18n";

class Register extends React.Component {
  constructor(props) {
    super(props);
    this.API = new ApiClient(props);
    this.state = {
      email: '',
      password: '',
      confirmPassword: '',
      name: '',
      lastName: '',
      address: '',
      phoneNumber: '',
      gender: '',
      license: '',
      errors: {
        email: false,
        password: false,
        confirmPassword: false,
        name: false,
        lastName: false,
        address: false,
        phoneNumber: false,
        gender: false,
        license: false,
      },
      current: 0,
      submitted: false,
      role: 'patient',
    };
  }


  componentDidMount() {
    const { role } = this.props.match.params;
    this.setState({ role });
  }

  handleChange(e) {
    e.preventDefault();
    const { name, value } = e.target;
    let errors = this.state.errors;

    switch (name) {
      case 'name':
        errors.name = value.length <= 0
        break;
      case 'lastName':
        errors.lastName = value.length <= 0
        break;
      case 'phoneNumber':
        errors.phoneNumber = !isValidPhone(value)
        break;
      case 'email':
        errors.email = !isValidEmail(value)
        break;
      case 'password':
        errors.password =  value.length < 6
        break;
      case 'confirmPassword':
        errors.confirmPassword = value.length < 6
        break;
      case 'address':
        errors.address = value.length <= 5
        break;
      case 'license':
        errors.license = value.length <= 0 || value.length >= 10
        break;
      case 'studies':
        errors.studies = value.length <= 0
        break;
      case 'gender':
        errors.studies = value !== 'F' || value !== 'M'
        break;
      default:
        break;
    }

    this.setState({errors, [name]: value })
  }

  handleSubmit() {
    const { email, password, confirmPassword, name, lastName, address, phoneNumber, gender, current,
      license, role, errors
    } = this.state;

    this.setState({ submitted: true });

    if(current === 0) {
      if(email && password && confirmPassword && this.passwordsMatch()) {
        this.setState({ current: current + 1, submitted: false })
      }
    } else if(current === 1) {
      if(role === 'patient') {
        if(name && lastName && phoneNumber && email && password && confirmPassword && !errors.name && !errors.lastName
          && !errors.phoneNumber && !errors.email && !errors.password) {
          const body = {
            firstName: name,
            lastName,
            email,
            password,
            passwordConfirmation: confirmPassword,
            phoneNumber,
          }
          this.API.post('/patient/register', body).then((response) => {
            console.log('succes', response)
            this.setState({ current: 2 });
          }).catch(err => {
            console.log({err});
          })
        }
      } else if(role === 'specialist') {
        if(name && lastName && phoneNumber && email && password && confirmPassword && address && gender && license
          && !errors.name && !errors.lastName && !errors.phoneNumber && !errors.email && !errors.password
          && !errors.address && !errors.gender && !errors.license) {
          const body = {
            firstName: name,
            lastName,
            email,
            password,
            passwordConfirmation: confirmPassword,
            phoneNumber,
            address,
            sex: gender,
            licence: license
          }
          console.log('body', body)
          this.API.post('/doctor/register', body).then((response) => {
            console.log('succes', response)
            this.setState({ current: 2 });
          }).catch(err => {
            console.log({err});
          })
        }
      }
    }
  }

  passwordsMatch() {
    const { password, confirmPassword, errors } = this.state;
    return password !== '' && confirmPassword !== '' && password === confirmPassword && !errors.password && !errors.confirmPassword ;
  }

  hasErrors(value, name) {
    if(this.state.submitted) {
      switch (name) {
        case 'name':
          return value.length <= 0
        case 'lastName':
          return value.length <= 0
        case 'phoneNumber':
          return !isValidPhone(value)
        case 'email':
          return !isValidEmail(value)
        case 'password':
          return  value.length < 6
        case 'confirmPassword':
          return value.length < 6
        case 'address':
          return value.length <= 5
        case 'license':
          return value.length <= 0 || value.length >= 10
        case 'studies':
          return  value.length <= 0
        case 'gender':
          return value !== 'F' || value !== 'M'
        default:
          return false;
      }
    }
    return false;
  }

  renderEmptyError(value) {
    if(value === '' && this.state.submitted) {
      return (
        <div className="text-danger">
            {i18n.t('register.emptyField')}
        </div>
      )
    }
  }

  renderSpecificError(value, name) {
    if(value === '' && this.state.submitted) {
      if(name === 'license') {
        return (
          <div className="text-danger">
              {i18n.t('register.licenseValidation')}
          </div>
        )
      }
    }
  }


  renderBasicForm(){
    const { email, password, confirmPassword, errors, submitted } = this.state;
    return(
      <div>
        <div className="form-group">
          <label className={(errors.email || this.hasErrors(email, 'email') ? 'text-danger' : '') + (!errors.email && email !== '' ? 'text-success' : '' )}>Email</label>
          <input name="email" value={email} type="email" className={'form-control ' + (errors.email ||  this.hasErrors(email, 'email')  ? 'is-invalid' : '') + (!errors.email && email !== '' ? 'is-valid' : '' )} aria-describedby="emailHelp" placeholder={i18n.t('login.placeHolderEmail')} onChange={(e) =>this.handleChange(e)}/>
          {
            errors.email &&
            <div className="text-danger">
              {i18n.t('register.emailValidation')}
            </div>
          }
          {
            !errors.email && email !== '' &&
            <div className="text-success">
              {i18n.t('register.validEmail')}
            </div>
          }
          {
            this.renderEmptyError(email)
          }
        </div>
        <div className="form-row">
          <div className="form-group col-md-6">
            <label className={(errors.password ||  this.hasErrors(password, 'password')) || (submitted && !this.passwordsMatch()) || (!this.passwordsMatch() && password !== '' && confirmPassword !== '') ? 'text-danger' : ''}>{i18n.t('login.password')}</label>
            <input name="password" value={password} type="password" className={'form-control ' + (errors.password ||  this.hasErrors(password, 'password') || submitted && !this.passwordsMatch() || (!this.passwordsMatch() && password !== '' && confirmPassword !== '') ? 'is-invalid' : '') + (this.passwordsMatch() ? 'is-valid' : '')} placeholder={i18n.t('login.placeHolderasPsword')} onChange={(e) => this.handleChange(e)}/>
            {
              errors.password &&
              <div className="text-danger">
                {i18n.t('register.validPassword')}
              </div>
            }
            {
              (submitted && !this.passwordsMatch() && password !== '' || (!this.passwordsMatch() && password !== '' && confirmPassword !== '')) &&
              <div className="text-danger">
                  {i18n.t('register.notMatchingPassword')}
              </div>
            }
            {
              this.passwordsMatch() &&
              <div className="text-success">
                  {i18n.t('register.matchingPassword')}
              </div>
            }
            {
              this.renderEmptyError(password)
            }
          </div>
          <div className="form-group col-md-6">
            <label className={errors.confirmPassword ||  this.hasErrors(confirmPassword, 'confirmPassword') || (!this.passwordsMatch() && password !== '' && confirmPassword !== '') ? 'text-danger' : ''}>{i18n.t('register.confirmPassword')}</label>
            <input name="confirmPassword" value={confirmPassword} type="password" className={'form-control ' + (errors.confirmPassword || this.hasErrors(confirmPassword, 'confirmPassword') || (!this.passwordsMatch() && password !== '' && confirmPassword !== '')? 'is-invalid' : '') + (this.passwordsMatch() ? 'is-valid' : '')} placeholder={i18n.t('register.placeHolderPassword')} onChange={(e) => this.handleChange(e)}/>
            {
              errors.confirmPassword &&
              <div className="text-danger">
                  {i18n.t('register.validPassword')}
              </div>
            }
            {
              this.passwordsMatch() &&
              <div className="text-success">
                  {i18n.t('register.matchingPassword')}
              </div>
            }
            {
              this.renderEmptyError(confirmPassword)
            }
          </div>
        </div>
      </div>
    )
  }

  renderPersonalForm() {
    const { name, lastName, address, phoneNumber, gender, errors, role, license } = this.state;
    return(
      <div>
        <div className="form-row">
          <div className="form-group col-md-6">
            <label className={errors.name || this.hasErrors(name, 'name') ? 'text-danger' : ''}>{i18n.t('register.name')}</label>
            <input name="name" value={name} type="text" className={'form-control ' + (errors.name || this.hasErrors(name, 'name') ? 'is-invalid' : '')} placeholder={i18n.t('register.placeHolderName')} onChange={(e) => this.handleChange(e)}/>
            {
              errors.name &&
              <div className="text-danger">
                  {i18n.t('register.validName')}
              </div>
            }
            {
              this.renderEmptyError(name)
            }
          </div>
          <div className="form-group col-md-6">
            <label className={errors.lastName || this.hasErrors(lastName, 'lastName')? 'text-danger' : ''}>{i18n.t('register.lastName')}</label>
            <input name="lastName" value={lastName} type="text" className={'form-control ' + (errors.lastName || this.hasErrors(lastName, 'lastName') ? 'is-invalid' : '')} placeholder={i18n.t('register.placeHolderLastName')} onChange={(e) => this.handleChange(e)}/>
            {
              errors.lastName &&
              <div className="text-danger">
                  {i18n.t('register.validLastName')}
              </div>
            }
            {
              this.renderEmptyError(lastName)
            }
          </div>
        </div>
        {
          role !== 'patient' &&
          <div className="form-group">
            <label className={errors.address || this.hasErrors(address, 'address') ? 'text-danger' : ''}>{i18n.t('register.address')}</label>
            <input name="address" value={address} type="text" className={'form-control ' + (errors.address || this.hasErrors(address, 'address') ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder={i18n.t('register.placeHolderAddress')} onChange={(e) =>this.handleChange(e)}/>
            {
              errors.address &&
              <div className="text-danger">
                  {i18n.t('register.validAddress')}
              </div>
            }
            {
              this.renderEmptyError(address)
            }
          </div>
        }
        <div className="form-group">
          <label className={errors.phoneNumber || this.hasErrors(phoneNumber, 'phoneNumber') ? 'text-danger' : ''}>{i18n.t('register.phoneNumber')}</label>
          <input name="phoneNumber" value={phoneNumber} type="tel" className={'form-control ' + (errors.phoneNumber || this.hasErrors(phoneNumber, 'phoneNumber') ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder={i18n.t('register.placeHolderPhoneNumber')} onChange={(e) =>this.handleChange(e)}/>
          {
            errors.phoneNumber &&
            <div className="text-danger">
                {i18n.t('register.validPhoneNumber')}
            </div>
          }
          {
            this.renderEmptyError(phoneNumber)
          }
        </div>
        {
          role !== 'patient' &&
            <div>
              <div className="form-group">
                <label className={errors.gender || this.hasErrors(gender, 'gender') ? 'text-danger' : ''}>{i18n.t('register.gender')}</label>
                <select name="gender" value={gender} className={'form-control ' + (errors.gender || this.hasErrors(gender, 'gender') ? 'is-invalid' : '')} onChange={(e) =>this.handleChange(e)}>
                  <option value="">{i18n.t('register.optionGender')}</option>
                  <option value="M">{i18n.t('register.male')}</option>
                  <option value="F">{i18n.t('register.female')}</option>
                </select>
                {
                  this.renderEmptyError(gender)
                }
              </div>
              <div className="form-group">
                <label className={this.hasErrors(license, 'license') ? 'text-danger' : ''}>{i18n.t('register.license')}</label>
                <input name="license" value={license} type="text" className={'form-control ' + (this.hasErrors(license, 'license') ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder={i18n.t('register.placeHolderLicense')} onChange={(e) =>this.handleChange(e)}/>
                {
                  this.renderSpecificError('license', license)
                }
              </div>
            </div>
        }
      </div>
    )
  }

  renderButton() {
    const { current } = this.state;
    if(current === 0) {
      return(
        <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn pull-right">{i18n.t('register.continueButton')}</div>
        )
    }
    if(current === 1) {
      return(
        <div className="row container">
          <div onClick={() => this.setState({ current: this.state.current - 1 })} className="btn btn-secondary mr-2">{i18n.t('register.backButton')}</div>
          <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn pull-right">{i18n.t('register.me')}</div>
        </div>
      )
    }
  }

  render() {
    const { current, role } = this.state;
    return (
      <div className="body-background">
        <div className="container col-12-sm w-p-20">
          <div className="login-card w-shadow">
            {
              role === 'patient' && current <= 1 &&
              <h3>{i18n.t('register.asPatient')}</h3>
            }
            {
              role === 'specialist' && current <= 1 &&
              <h3>{i18n.t('register.asDoctor')}</h3>
            }
            {
              current <= 1 &&
              <div style={{ marginTop: 32, marginBottom: 16 }}>
                <Steps labelPlacement="vertical" current={current} icons={ <FontAwesomeIcon icon={faCoffee}/>}>
                  <Step title={i18n.t('register.basicInfo')}/>
                  <Step title={i18n.t('register.personalInfo')}/>
                </Steps>
              </div>
            }
            {
              current === 2 &&
              <div>
                <FontAwesomeIcon icon={faCheckCircle} color="#46ce23" size="4x"/>
                <h3 className="mt-4">{i18n.t('register.welcome')}</h3>
                <p>{i18n.t('register.emailMessage')}</p>
                <Link className="btn btn-primary custom-btn" to="/">{i18n.t('register.toHome')}</Link>
              </div>
            }

            <form className="mb-4">
              {
                current === 0 &&
                this.renderBasicForm() //TODO Pasar a componentes para evitar re-render
              }
              {
                current === 1 &&
                this.renderPersonalForm()
              }
            </form>
            {this.renderButton()}
            {
              current <= 1 &&
              <div style={{ marginTop: 8 }}>
                <small><Link to="/">{i18n.t('login.cancel')}</Link></small>
              </div>
            }
          </div>
        </div>

      </div>
    )
  }
}

export default Register;
