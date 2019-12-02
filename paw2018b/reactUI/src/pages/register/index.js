import React from 'react';
import { Link } from 'react-router-dom';
import { isValidEmail } from '../../utils/validations';
import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';
import Steps, { Step } from 'rc-steps';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoffee, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import Form from 'react-bootstrap/Form';
import Tab from 'react-bootstrap/Tab';
import Nav from 'react-bootstrap/Nav';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Badge from 'react-bootstrap/Badge';
import Select from 'react-select';
import fetchApi from '../../utils/api'

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
    insurances: new Map(),
    insurancePlans: new Map(),
    workingHours: new Map(),
    allSpecialties: [],
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

  componentWillMount() {
    fetchApi('/specialties', 'GET')
      .then(response => {
        let allSpecialties = [];
        response.specialties.map(specialty => allSpecialties.push({value: specialty.id, label: specialty.speciality}))
        this.setState({allSpecialties}, console.log(allSpecialties));
      })
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
        errors.address = value.length <= 0
        break;
      case 'license':
        errors.license = value.length <= 0
        break;
      case 'studies':
        errors.studies = value.length <= 0
        break;
      default:
        break;
    }

    this.setState({errors, [name]: value }, ()=> {
      console.log(errors)
    })
  }

  handleCheckboxChange(e) {
    const item = e.target.name;
    const isChecked = e.target.checked;
    console.log(item, isChecked);
    this.setState(prevState => ({ languages: prevState.languages.set(item, isChecked), submitted: false }));
  }

  handleSubmit() {
    const { email, password, confirmPassword, name, lastName, address, phoneNumber, gender, current,
      license, photo, studies, languages, specialties, insurances, insurancePlans, workingHours
    } = this.state;
    this.setState({ submitted: true });
    if(current === 0) {
      if(email && password && confirmPassword && this.passwordsMatch()) {
        this.setState({ current: current + 1, submitted: false })
      }
    } else if(current === 1) {
      if(name && lastName && address && phoneNumber && gender) {
        this.setState({ current: current + 1, submitted: false })
      }
    } else if(current === 2) {
      if(license && studies && languages && languages.size > 0 && specialties.length > 0
      && insurancePlans && insurancePlans.size > 0 && workingHours && workingHours.size > 0) {
        this.setState({ current: current + 1, submitted: false })
      } //TODO add photo
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

  handleSelect(s) {
    const specialties = this.state.specialties;
    if(specialties.indexOf(s.label) === -1){
      this.setState({ specialties: [...specialties, s.label], submitted: false });
    }
  }

  removeSpecialty(s){
    let specialties = this.state.specialties.filter(e => e !== s)
    this.setState({ specialties })
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
    const { license, errors, languages, insurances, insurancePlans, studies, specialties, submitted, current, allSpecialties, workingHours } = this.state;
    const LANGUAGES = ['Español', 'Ingles', 'Aleman'];
    const INSURANCES = [
      {
        name: 'OSDE',
        plans: ['210', '310', '410']
      },
      {
        name: 'Swiss Medical',
        plans: ['210', '310', '410']
      }
    ]
    const DAYS = ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado', 'Domingo'];
    return(
      <div className="mb-3">
        <div className="form-group">
          <label className={errors.studies || this.isEmpty(studies) ? 'text-danger' : ''}>Estudios</label>
          <textarea name="studies" value={studies} type="text" rows="3" className={'form-control ' + (errors.studies || this.isEmpty(studies) ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder="Ingresa tus estudios" onChange={(e) =>this.handleChange(e)}/>
          {
            errors.studies &&
            <div className="text-danger">
              Ingresa informacion sobre tus estudios
            </div>
          }
          {
            this.renderEmptyError(studies)
          }
        </div>
        <div className="mb-1">
          <Form>
            <Form.Group>
              <Form.Label className={this.isValidMap(languages) ? '' : 'text-danger'} style={{ marginRight: 32 }}>
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
              {
                !this.isValidMap(languages) &&
                <label className="text-danger">Selecciona uno o mas lenguajes</label>
              }
            </Form.Group>
          </Form>
        </div>
        <div className="form-group">
          <label className={this.isEmpty(license) ? 'text-danger' : ''}>Licencia Profesional</label>
          <input name="license" value={license} type="text" className={'form-control ' + (this.isEmpty(license) ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder="Ingresa tu licencia" onChange={(e) =>this.handleChange(e)}/>
          {
            this.renderEmptyError(license)
          }
        </div>
        <div>
          <label className={specialties.length === 0 && submitted ? 'text-danger' : ''}>Especialidades</label>
          <Select
            onChange={(e) => this.handleSelect(e)}
            options={allSpecialties}
            placeholder="Busca tus especialidades"
            className={specialties.length === 0 && submitted ? 'text-danger is-invalid' : ''}
            isLoading={allSpecialties.length === 0}
          />
          {
            specialties.length === 0 && submitted &&
            <label className="text-danger">Selecciona una o mas especialidades</label>
          }
        </div>
        <div className="col-sm-12 p-0 mt-3 mb-3">
          {
            specialties.length > 0 &&
            <small className="mr-2">Estas son las especialidades que agregaste:</small>
          }
          {
            specialties.map((s, index) => {
              return(
                 <Badge key={index} className="badge-waldoc" onClick={() => this.removeSpecialty(s)}>
                   {s} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
                 </Badge>
              )
            })
          }
        </div>
        <div className="mb-3">
          <Tab.Container id="left-tabs-example" defaultActiveKey={INSURANCES[0].name}>
            <Form.Label className={!this.isValidMap(insurancePlans) ? 'text-danger' : '' } style={{ marginRight: 32 }}>
              Selecciona los planes de las prepagas con las que trabajas
            </Form.Label>
            <Row>
              <Col sm={3}>
                <Nav variant="pills" className="flex-column">
                  {
                    INSURANCES.map((ins) => {
                      return (
                        <Nav.Item key={ins.name}>
                          <Nav.Link eventKey={ins.name}>{ins.name}</Nav.Link>
                        </Nav.Item>
                      )
                    })
                  }
                </Nav>
              </Col>
              <Col sm={9}>
                <Tab.Content>
                  {
                    INSURANCES.map((ins) => {
                      return (
                        <Tab.Pane eventKey={ins.name} key={ins.name}>
                          {
                            ins.plans.map((plan) => {
                              return (
                                <Form.Check
                                  checked={insurancePlans.get(plan)}
                                  onChange={(e) => this.handleCheckboxChange(e)}
                                  name={plan}
                                  label={plan}
                                  key={plan + ins.name}
                                  type="switch"
                                  id={plan + ins.name}
                                />
                              )
                            })
                          }
                        </Tab.Pane>
                      )
                    })
                  }
                </Tab.Content>
              </Col>
            </Row>
            {
              !this.isValidMap(insurancePlans) &&
              <Form.Label className={'text-danger'} style={{ marginRight: 32 }}>
                Selecciona al menos un plan
              </Form.Label>
            }
          </Tab.Container>
        </div>
        <label className={(!this.isValidMap(workingHours) ? 'text-danger ' : '') + 'mt-1'}>Ingresa tus horarios de trabajo</label>
        {
          DAYS.map(day => {
            let start = this.getMapValue(workingHours, day, true);
            let end = this.getMapValue(workingHours, day, false);
            return(
              <div key={day}>
                <div className="form-row">
                  <div className="form-group col-md-2 pt-2">
                    <label className={!this.isValidDay(day) ? 'text-danger' : ''}>{day}</label>
                  </div>
                  <div className="form-group col-md-5">
                    <input name="start" value={start} type="number" min="0" max="24" className={'form-control ' + (this.isValidDay(day) ? '' : 'is-invalid')} placeholder="Inicio (Ej: 9Hs)" onChange={(e) => this.handleAddWorkingHours(e, day, true)}/>
                  </div>
                  <div className="form-group col-md-5">
                    <input name="end" value={end} type="number" min="0" max="24" className={'form-control ' + (this.isValidDay(day) ? '' : 'is-invalid')} placeholder="Fin (Ej: 18Hs)" onChange={(e) => this.handleAddWorkingHours(e, day, false)}/>
                  </div>
                </div>
              </div>
            )
          })
        }
        {
          !this.isValidMap(workingHours) &&
          <label className={'text-danger '}>Completá al menos un dia</label>
        }
      </div>
    )
  }

  getMapValue(map, day, start) {
    if(map.get(day) && start && map.get(day).start) {
      return map.get(day).start;
    }
    if(map.get(day) && !start && map.get(day).end) {
      return map.get(day).end;
    }
    return '';
  }

  handleAddWorkingHours(e,day, start) {
    const time = e.target.value;
    const workingHours = this.state.workingHours
    if(!workingHours.get(day)){
      if(start) {
        workingHours.set(day, { name: day, start: time })
      } else {
        workingHours.set(day, { name: day, end: time })
      }
    } else {
      let w = workingHours.get(day)
      if(start) {
        workingHours.set(day, { ...w, start: time})
      } else {
        workingHours.set(day, { ...w, end: time})
      }
    }
    this.setState({ workingHours, submitted: false });
    console.log(this.state.workingHours);
  }

  isValidMap(map) {
    if(this.state.submitted && map && map.size === 0) {
      return false
    }
    return true;
  }

  isValidDay(day) {
    const workingHours = this.state.workingHours
    if(!this.state.submitted || !workingHours.get(day)){
      return true;
    }
    let w = workingHours.get(day);
    if(!w.start || !w.end){
      return false
    }
    const start = parseInt(w.start)
    const end = parseInt(w.end);
    return start <= 24 && start >= 0 && end <= 24 && start >= 0 && start < end;
  }



  renderButton() {
    const { current } = this.state;
    if(current === 0) {
      return(
        <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn pull-right">Continuar</div>
        )
    }
    if(current === 1) {
      return(
        <div className="row container">
          <div onClick={() => this.setState({ current: this.state.current - 1 })} className="btn btn-secondary mr-2">Atras</div>
          <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn pull-right">Continuar</div>
        </div>
      )
    }
    if(current === 2) {
      return(
      <div className="row container">
        <div onClick={() => this.setState({ current: this.state.current - 1 })} className="btn btn-secondary mr-2">Atras</div>
        <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn pull-right">Registrarme</div>
      </div>
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
                current === 0 &&
                this.renderBasicForm() //TODO Pasar a componentes para evitar re-render
              }
              {
                current === 1 &&
                this.renderPersonalForm()
              }
              {
                current === 2 &&
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
