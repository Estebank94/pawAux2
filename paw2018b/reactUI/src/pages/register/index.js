import React from 'react';
import { Link } from 'react-router-dom';
import { isValidEmail } from '../../utils/validations'
import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';
import Steps, { Step } from 'rc-steps';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoffee } from '@fortawesome/free-solid-svg-icons';
import Form from 'react-bootstrap/Form'

class Register extends React.Component {
  state = {
    email: '',
    password: '',
    confirmPassword: '',
    name: '',
    lastName: '',
    address: '',
    phoneNumber: '',
    gender: '',
    license: '',
    photo: '',
    studies: '',
    languages: new Map(),
    specialties: [],
    insurances: [],
    workingHours: [],
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
      photo: false,
      studies: false,
      languages: false,
      specialties: false,
      insurances: false,
      workingHours: false,
    },
    current: 0,
    submitted: false,
  };

  handleChange(e) {
    e.preventDefault();
    const { name, value } = e.target;
    let errors = this.state.errors;

    switch (name) {
      case 'name':
        errors.name = value.length < 3
        break;
      case 'lastName':
        errors.lastName = value.length < 3
        break;
      case 'email':
        errors.email = !isValidEmail(value)
        break;
      case 'password':
        errors.password =  value.length < 8
        break;
      case 'confirmPassword':
        errors.confirmPassword = value.length < 8
        break;
      case 'address':
        errors.address = value.length < 3
        break;
      default:
        break;
    }

    this.setState({errors, [name]: value}, ()=> {
      console.log(errors)
    })
  }

  handleCheckboxChange(e) {
    const item = e.target.name;
    const isChecked = e.target.checked;
    console.log(item, isChecked);
    this.setState(prevState => ({ languages: prevState.languages.set(item, isChecked) }));
  }

  handleSubmit() {
    const { email, password, confirmPassword, name, lastName, address, phoneNumber, gender, current } = this.state;
    this.setState({ submitted: true });
    if(current === 0) {
      if(email && password && confirmPassword && this.passwordsMatch()) {
        this.setState({ current: current + 1, submitted: false })
      }
    } else if(current === 1) {
      if(name && lastName && address && phoneNumber && gender) {
        this.setState({ current: current + 1, submitted: false })
      }
    }

  }


  passwordsMatch() {
    const { password, confirmPassword, errors } = this.state;
    return password !== '' && confirmPassword !== '' && password === confirmPassword && !errors.password && !errors.confirmPassword ;
  }

  isEmpty(value) {
    if(value === '' && this.state.submitted) {
      return true;
    }
    return false;
  }

  renderEmptyError(value) {
    if(value === '' && this.state.submitted) {
      return (
        <div className="text-danger">
          El campo no puede quedar vacio
        </div>
      )
    }
  }

  renderBasicForm(){
    const { email, password, confirmPassword, errors, submitted } = this.state;
    return(
      <div>
        <div className="form-group">
          <label className={(errors.email || this.isEmpty(email) ? 'text-danger' : '') + (!errors.email && email !== '' ? 'text-success' : '' )}>Email</label>
          <input name="email" value={email} type="email" className={'form-control ' + (errors.email || this.isEmpty(email)  ? 'is-invalid' : '') + (!errors.email && email !== '' ? 'is-valid' : '' )} aria-describedby="emailHelp" placeholder="Ingresa tu email" onChange={(e) =>this.handleChange(e)}/>
          {
            errors.email &&
            <div className="text-danger">
              Ingrese una direccion de mail valida
            </div>
          }
          {
            !errors.email && email !== '' &&
            <div className="text-success">
              Direccion de email valida
            </div>
          }
          {
            this.renderEmptyError(email)
          }
        </div>
        <div className="form-row">
          <div className="form-group col-md-6">
            <label className={(errors.password || this.isEmpty(password)) || (submitted && !this.passwordsMatch()) || (!this.passwordsMatch() && password !== '' && confirmPassword !== '') ? 'text-danger' : ''}>Contraseña</label>
            <input name="password" value={password} type="password" className={'form-control ' + (errors.password || this.isEmpty(password) || submitted && !this.passwordsMatch() || (!this.passwordsMatch() && password !== '' && confirmPassword !== '') ? 'is-invalid' : '') + (this.passwordsMatch() ? 'is-valid' : '')} placeholder="Ingresa tu contraseña" onChange={(e) => this.handleChange(e)}/>
            {
              errors.password &&
              <div className="text-danger">
                La contraseña debe tener al menos 8 caracteres
              </div>
            }
            {
              (submitted && !this.passwordsMatch() && password !== '' || (!this.passwordsMatch() && password !== '' && confirmPassword !== '')) &&
              <div className="text-danger">
                Las contraseñas no coinciden
              </div>
            }
            {
              this.passwordsMatch() &&
              <div className="text-success">
                Las contraseñas coinciden
              </div>
            }
            {
              this.renderEmptyError(password)
            }
          </div>
          <div className="form-group col-md-6">
            <label className={errors.confirmPassword || this.isEmpty(confirmPassword) || (!this.passwordsMatch() && password !== '' && confirmPassword !== '') ? 'text-danger' : ''}>Confirmar Contraseña</label>
            <input name="confirmPassword" value={confirmPassword} type="password" className={'form-control ' + (errors.confirmPassword || this.isEmpty(confirmPassword) || (!this.passwordsMatch() && password !== '' && confirmPassword !== '')? 'is-invalid' : '') + (this.passwordsMatch() ? 'is-valid' : '')} placeholder="Ingresa tu contraseña" onChange={(e) => this.handleChange(e)}/>
            {
              errors.confirmPassword &&
              <div className="text-danger">
                La contraseña debe tener al menos 8 caracteremmmms
              </div>
            }
            {
              this.passwordsMatch() &&
              <div className="text-success">
                Las contraseñas coinciden
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
    const { name, lastName, address, phoneNumber, gender, errors } = this.state;
    return(
      <div>
        <div className="form-row">
          <div className="form-group col-md-6">
            <label className={errors.name || this.isEmpty(name) ? 'text-danger' : ''}>Nombre</label>
            <input name="name" value={name} type="text" className={'form-control ' + (errors.name || this.isEmpty(name) ? 'is-invalid' : '')} placeholder="Ingresa tu nombre" onChange={(e) => this.handleChange(e)}/>
            {
              errors.name &&
              <div className="text-danger">
                Ingresa un nombre valido
              </div>
            }
            {
              this.renderEmptyError(name)
            }
          </div>
          <div className="form-group col-md-6">
            <label className={errors.lastName || this.isEmpty(lastName)? 'text-danger' : ''}>Apellido</label>
            <input name="lastName" value={lastName} type="text" className={'form-control ' + (errors.lastName || this.isEmpty(lastName) ? 'is-invalid' : '')} placeholder="Ingresa tu contraseña" onChange={(e) => this.handleChange(e)}/>
            {
              errors.lastName &&
              <div className="text-danger">
                Ingresa un apellido
              </div>
            }
            {
              this.renderEmptyError(lastName)
            }
          </div>
        </div>
        <div className="form-group">
          <label className={errors.address || this.isEmpty(address) ? 'text-danger' : ''}>Direccion</label>
          <input name="address" value={address} type="text" className={'form-control ' + (errors.address || this.isEmpty(address) ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder="Ingresa tu email" onChange={(e) =>this.handleChange(e)}/>
          {
            errors.address &&
            <div className="text-danger">
              Ingresa una direccion valida
            </div>
          }
          {
            this.renderEmptyError(address)
          }
        </div>
        <div className="form-group">
          <label className={errors.phoneNumber || this.isEmpty(phoneNumber) ? 'text-danger' : ''}>Telefono</label>
          <input name="phoneNumber" value={phoneNumber} type="tel" className={'form-control ' + (errors.phoneNumber || this.isEmpty(phoneNumber) ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder="Ingresa tu email" onChange={(e) =>this.handleChange(e)}/>
          {
            errors.phoneNumber &&
            <div className="text-danger">
              Ingresa un numero de telefono valido
            </div>
          }
          {
            this.renderEmptyError(phoneNumber)
          }
        </div>
        <div className="form-group">
          <label className={errors.gender || this.isEmpty(gender) ? 'text-danger' : ''}>Sexo</label>
          <select name="gender" value={gender} className={'form-control ' + (errors.gender || this.isEmpty(gender) ? 'is-invalid' : '')} onChange={(e) =>this.handleChange(e)}>
            <option value="">Elegir una opcion</option>
            <option value="male">Masculino</option>
            <option value="female">Femenino</option>
            <option value="nonbinary">No Binario</option>
            <option value="other">Otro</option>
          </select>
          {
            this.renderEmptyError(gender)
          }
        </div>
      </div>
    )
  }

  renderProfessionalForm() {
    const { license, errors, languages, studies, submitted, current } = this.state;
    const LANGUAGES = ['Español', 'Ingles', 'Aleman'];
    return(
      <div>
        <div className="form-group">
          <label className={errors.license || this.isEmpty(license) ? 'text-danger' : ''}>Licencia Profesional</label>
          <input name="license" value={license} type="text" className={'form-control ' + (errors.license || this.isEmpty(license) ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder="Ingresa tu licencia" onChange={(e) =>this.handleChange(e)}/>
          {
            errors.address &&
            <div className="text-danger">
              Ingresa una licencia valida
            </div>
          }
          {
            this.renderEmptyError(license)
          }
        </div>
        <div className="form-group">
          <label className={errors.studies || this.isEmpty(studies) ? 'text-danger' : ''}>Estudios</label>
          <textarea name="license" value={studies} type="text" rows="3" className={'form-control ' + (errors.studies || this.isEmpty(studies) ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder="Ingresa tus estudios" onChange={(e) =>this.handleChange(e)}/>
          {
            errors.address &&
            <div className="text-danger">
              Ingresa informacion sobre tus estudios
            </div>
          }
          {
            this.renderEmptyError(studies)
          }
        </div>
        <div className="mb-3">
          <Form>
            <Form.Group controlId="formPlaintextPassword">
              <Form.Label style={{ marginRight: 32 }}>
                Idiomas
              </Form.Label>
              {
                LANGUAGES.map((lang, index) => {
                  return (
                    <Form.Check
                      checked={languages.get(lang)}
                      onChange={(e) => this.handleCheckboxChange(e)}
                      custom
                      inline
                      name={lang}
                      label={lang}
                      key={lang + index}
                      type="switch"
                      id={lang + index}
                    />
                  )
                })
              }
            </Form.Group>
          </Form>
        </div>
      </div>
    )
  }



  renderButton() {
    const { current } = this.state;
    if(current === 0) {
      return(
        <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn pull-right">Continuar</div>
        )
    }
  }



  render() {
    const { current } = this.state;
    const role = 'specialist';
    return (
      <div className="body-background">
        <div className="container col-12-sm w-p-20">
          <div className="login-card w-shadow">
            {
              role === 'patient' &&
              <div>
                <h3>Registrate como paciente</h3>
                <p style={{marginBottom: 24 }}>Estas a unos pasos de acceder a los mejores medicos!</p>
              </div>
            }
            {
              role === 'specialist' &&
              <div>
                <h3>Registrate como especialista</h3>
                <div style={{ marginTop: 32, marginBottom: 16 }}>
                  <Steps labelPlacement="vertical" current={current} icons={ <FontAwesomeIcon icon={faCoffee}/>}>
                    <Step title="Datos Basicos"/>
                    <Step title="Datos Personales"/>
                    <Step title="Datos Profesionales"/>
                  </Steps>
                </div>
              </div>
            }

            <form>

              {
                current === 3 &&
                this.renderBasicForm() //TODO Pasar a componentes para evitar re-render
              }
              {
                current === 1 &&
                this.renderPersonalForm()
              }
              {
                this.renderProfessionalForm()
              }

            </form>
            {this.renderButton()}
            <div style={{ marginTop: 8 }}>
              <small><Link>Cancelar</Link></small>
            </div>
          </div>
        </div>

      </div>
    )
  }
}

export default Register;
