import React from 'react';
import { Link } from 'react-router-dom';
import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';
import Steps, { Step } from 'rc-steps';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoffee, faTimesCircle, faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import Form from 'react-bootstrap/Form';
import Tab from 'react-bootstrap/Tab';
import Nav from 'react-bootstrap/Nav';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Badge from 'react-bootstrap/Badge';
import Select from 'react-select';
import { ApiClient } from '../../utils/apiClient';
import i18n from "../../../i18n";

class CompleteProfile extends React.Component {
  constructor(props) {
    super(props);
    this.API = new ApiClient(props);
    this.state = {
      photo: '',
      studies: '',
      languages: new Map(),
      specialties: [],
      insurances: new Map(),
      insurancePlans: new Map(),
      workingHours: new Map(),
      allSpecialties: [],
      allInsurances: [],
      errors: {
        photo: false,
        studies: false,
        languages: false,
        specialties: false,
        insurances: false,
        workingHours: false,
      },
      submitted: false,
    };
  }


  componentWillMount() {
    this.setState({ role });
    this.API.get('/specialties')
      .then(response => {
        let allSpecialties = [];
        response.data.specialties.map(specialty => allSpecialties.push({value: specialty.id, label: specialty.speciality}))
        this.setState({allSpecialties});
      })
    this.API.get('/insurances')
      .then(response => {
        console.log('insurances', response);
        // let allInsurances = [];
        // response.data.insurances.map(specialty => allInsurances.push({value: specialty.id, label: specialty.speciality}))
        // this.setState({allSpecialties});
      })
  }

  handleChange(e) {
    e.preventDefault();
    const { name, value } = e.target;
    let errors = this.state.errors;

    switch (name) {
      case 'studies':
        errors.studies = value.length <= 0
        break;
      default:
        break;
    }

    this.setState({errors, [name]: value })
  }

  handleCheckboxChange(e) {
    const item = e.target.name;
    const isChecked = e.target.checked;
    this.setState(prevState => ({ languages: prevState.languages.set(item, isChecked), submitted: false }));
  }

  handleSubmit() {
    const { email, password, confirmPassword, name, lastName, address, phoneNumber, gender, current,
      license, photo, studies, languages, specialties, insurances, insurancePlans, workingHours, role
    } = this.state;

    this.setState({ submitted: true });

    if(current === 0) {
      if(email && password && confirmPassword && this.passwordsMatch()) {
        this.setState({ current: current + 1, submitted: false })
      }
    } else if(current === 1) {
      if(role === 'patient') {
        if(name && lastName && phoneNumber && email && password) {
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
        if(name && lastName && phoneNumber && email && password && address && gender && license) {
          const body = {
            firstName: name,
            lastName,
            email,
            password,
            passwordConfirmation: confirmPassword,
            phoneNumber,
            address,
            sex: gender,
            license
          }
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
            {i18n.t('register.emptyField')}
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
          <label className={errors.studies || this.isEmpty(studies) ? 'text-danger' : ''}>{i18n.t('register.studies')}</label>
          <textarea name="studies" value={studies} type="text" rows="3" className={'form-control ' + (errors.studies || this.isEmpty(studies) ? 'is-invalid' : '')} aria-describedby="emailHelp" placeholder={i18n.t('register.placeHolderStudies')} onChange={(e) =>this.handleChange(e)}/>
          {
            errors.studies &&
            <div className="text-danger">
                {i18n.t('register.descriptionStudies')}
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
                {i18n.t('register.languages')}
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
                <label className="text-danger">{i18n.t('register.selectLanguages')}</label>
              }
            </Form.Group>
          </Form>
        </div>
        <div>
          <label className={specialties.length === 0 && submitted ? 'text-danger' : ''}>{i18n.t('register.speciality')}</label>
          <Select
            onChange={(e) => this.handleSelect(e)}
            options={allSpecialties}
            placeholder={i18n.t('register.placeHolderSpeciality')}
            className={specialties.length === 0 && submitted ? 'text-danger is-invalid' : ''}
            isLoading={allSpecialties.length === 0}
          />
          {
            specialties.length === 0 && submitted &&
            <label className="text-danger">{i18n.t('register.selectSpeciality')}</label>
          }
        </div>
        <div className="col-sm-12 p-0 mt-3 mb-3">
          {
            specialties.length > 0 &&
            <small className="mr-2">{i18n.t('register.selectedSpeciality')}</small>
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
                {i18n.t('register.selectInsurance')}
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
                  {i18n.t('register.selectInsuranceLabel')}
              </Form.Label>
            }
          </Tab.Container>
        </div>
        <label className={(!this.isValidMap(workingHours) ? 'text-danger ' : '') + 'mt-1'}>{i18n.t('register.workingHours')}</label>
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
                    <input name="start" value={start} type="number" min="0" max="24" className={'form-control ' + (this.isValidDay(day) ? '' : 'is-invalid')} placeholder={i18n.t('register.placeHolderWorkingHoursStart')} onChange={(e) => this.handleAddWorkingHours(e, day, true)}/>
                  </div>
                  <div className="form-group col-md-5">
                    <input name="end" value={end} type="number" min="0" max="24" className={'form-control ' + (this.isValidDay(day) ? '' : 'is-invalid')} placeholder={i18n.t('register.placeHolderWorkingHoursFinish')} onChange={(e) => this.handleAddWorkingHours(e, day, false)}/>
                  </div>
                </div>
              </div>
            )
          })
        }
        {
          !this.isValidMap(workingHours) &&
          <label className={'text-danger '}>{i18n.t('register.errorDay')}</label>
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
    return (
      <div className="body-background">
        <div className="container col-12-sm w-p-20">
          <div className="login-card w-shadow">
            <h3>{i18n.t('register.completeProfTitle')}</h3>

            <form className="mb-4">
              {
                this.renderProfessionalForm()
              }
            </form>
          </div>
        </div>

      </div>
    )
  }
}

export default CompleteProfile;
