import React, { useMemo } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';
import BounceLoader from 'react-spinners/BounceLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCoffee, faTimesCircle, faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import Form from 'react-bootstrap/Form';
import Tab from 'react-bootstrap/Tab';
import Nav from 'react-bootstrap/Nav';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Badge from 'react-bootstrap/Badge';
import Select from 'react-select';
import { ApiClient } from '../../../utils/apiClient';
import i18n from "../../../i18n";
import Dropzone from "react-dropzone";
import moment from 'moment';

class CompleteProfile extends React.Component {
  constructor(props) {
    super(props);
    this.API = new ApiClient(props);
    this.state = {
      loading: true,
      photo: '',
      studies: '',
      languages: new Map(),
      specialties: [],
      insurances: new Map(),
      insurancePlans: new Map(),
      workingHours: new Map(),
      allSpecialties: [],
      allInsurances: [],
      files: [],
      certificate: '',
      errors: {
        photo: false,
        studies: false,
        languages: false,
        specialties: false,
        insurances: false,
        workingHours: false,
        certificate: false,
      },
      uploadError: false,
      submitted: false,
      success: false
    };
  }


  async componentWillMount() {
    await this.API.get('/specialties')
      .then(response => {
        let allSpecialties = [];
        response.data.specialties.map(specialty => allSpecialties.push({value: specialty, label: specialty}))
        this.setState({allSpecialties});
      })
    await this.API.get('/insurances')
      .then(response => {
        let allInsurances = [];
        response.data.insurances.map(insurance => allInsurances.push({name: insurance.name, plans: insurance.plans}))
        this.setState({allInsurances});
      })
    this.setState({ loading: false })
  }

  componentWillUnmount() {
    this.state.files.forEach(file => URL.revokeObjectURL(file.preview));
  }

  addFile = file => {
    this.setState({
      files: file.map(file =>
        Object.assign(file, {
          preview: URL.createObjectURL(file)
        })
      )
    });
  };

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

  handleCheckboxChange(e, name) {
    const item = e.target.name;
    const isChecked = e.target.checked;
    if(name === 'languages') {
      this.setState(prevState => ({ languages: prevState.languages.set(item, isChecked), submitted: false }));
    } else {
      this.setState(prevState => ({ insurancePlans: prevState.insurancePlans.set(item, isChecked), submitted: false }));
    }
  }

  handleSubmit() {
    const { photo, studies, languages, specialties, insurancePlans, workingHours } = this.state;

    this.setState({ submitted: true });

    this.getMapKeys(languages);

    this.getWorkingHours(workingHours);

    if(photo && studies && !this.mapIsEmpty(languages) && specialties.length > 0  && !this.mapIsEmpty(insurancePlans) && !this.hasWorkingHoursError(workingHours)) {
      const body = {
        description: {
          certificate: 'cer',
          languages: this.arrayToString(this.getMapKeys(languages)),
          education: studies,
        },
        specialty: specialties,
        insurancePlan: this.getMapKeys(insurancePlans),
        workingHours: this.getWorkingHours(workingHours)
      }

      const conf = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }

      let bodyFormData = new FormData();
      bodyFormData.append('file', this.state.files[0])

      this.setState({ loading: true })
      this.API.put('/doctor/uploadPicture', bodyFormData, conf ).then(() => {
        this.API.post('/doctor/registerProfessional', body).then(() => {
          this.setState({ loading: false, success: true })
        })
      })

    }
  }

  getDay(name) {
    switch(name) {
      case 'monday':
        return 1
      case 'tuesday':
        return 2
      case 'wednesday':
        return 3
      case 'thursday':
        return 4
      case 'friday':
        return 5
      case 'saturday':
        return 6
      case 'sunday':
        return 7
      default:
        return 1
    }
  }

  hasWorkingHoursError(wh) {
    if(!wh) {
      return true;
    }

    for (let [key, value] of wh.entries()) {
      if(moment(value.start, 'HH:mm').isAfter(value.end, 'HH:mm')){
        return true
      }
    }

    return false;

  }

  getWorkingHours(wh) {
    let res = [];

    if(!wh) {
      return res;
    }

    for (let [key, value] of wh.entries()) {
      if(value.start && value.end){
        res.push({ dayOfWeek: this.getDay(value.name), startTime: value.start, finishTime: value.end })
      }
    }

    return res;

  }

  getMapKeys(map) {
    let keys = []

    if(!map) {
      return keys
    }

    for (let [key, value] of map.entries()) {
      if(value){
        keys.push(key)
      }
    }

    return keys;
  }

  arrayToString(ar) {
    let str = ''
    ar.map(v => str += (v + ' '));
    return str;
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

  mapIsEmpty(map) {
    if(!map) {
      return true
    }

    let res = true
    map.forEach(e => { if(e === true) {
      res = false
    }})

    return res
  }


  renderProfessionalForm() {
    const { errors, languages, certificate, insurancePlans, studies, specialties, submitted, allSpecialties, workingHours, allInsurances } = this.state;
    const LANGUAGES = ['Español', 'Ingles', 'Aleman'];
    const DAYS = ['monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday', 'sunday'];
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
        <div className="form-group">
          <label className={errors.certificate || this.isEmpty(certificate) ? 'text-danger' : ''}>{i18n.t('register.certificate')}</label>
          <textarea name="certificate" value={certificate} type="text" rows="1" className={'form-control ' + (errors.certificate || this.isEmpty(certificate) ? 'is-invalid' : '')} placeholder={i18n.t('register.placeHolderCertificate')} onChange={(e) =>this.handleChange(e)}/>
          {
            errors.certificate &&
            <div className="text-danger">
              {i18n.t('register.descriptionCertificate')}
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
                      onChange={(e) => this.handleCheckboxChange(e, 'languages')}
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
          <Tab.Container id="left-tabs-example" defaultActiveKey={allInsurances[0].name}>
            <Form.Label className={!this.isValidMap(insurancePlans) ? 'text-danger' : '' } style={{ marginRight: 32 }}>
                {i18n.t('register.selectInsurance')}
            </Form.Label>
            <Row>
              <Col sm={3}>
                <Nav variant="pills" className="flex-column">
                  {
                    allInsurances.map((ins) => {
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
                    allInsurances.map((ins) => {
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
                    <label className={!this.isValidDay(day) ? 'text-danger' : ''}>{i18n.t(`week.${day}`)}</label>
                  </div>
                  <div className="form-group col-md-5">
                    <input name="start" value={start} type="time" min="00:00" max="24:00" className={'form-control ' + (this.isValidDay(day) ? '' : 'is-invalid')} placeholder={i18n.t('register.placeHolderWorkingHoursStart')} onChange={(e) => this.handleAddWorkingHours(e, day, true)}/>
                  </div>
                  <div className="form-group col-md-5">
                    <input name="end" value={end} type="time" min="00:00" max="24:00" className={'form-control ' + (this.isValidDay(day) ? '' : 'is-invalid')} placeholder={i18n.t('register.placeHolderWorkingHoursFinish')} onChange={(e) => this.handleAddWorkingHours(e, day, false)}/>
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

  addFile = file => {
    this.setState({
      files: file.map(file =>
        Object.assign(file, {
          preview: URL.createObjectURL(file)
        })
      )
    });
  };

  onDrop = (accepted, rejected) => {
    if (Object.keys(rejected).length !== 0) {
      this.setState({ uploadError: true });
    } else {
      this.addFile(accepted);
      this.setState({ uploadError: false });

      var blobPromise = new Promise((resolve, reject) => {
        const reader = new window.FileReader();
        reader.readAsDataURL(accepted[0]);
        reader.onloadend = () => {
          const base64data = reader.result;
          resolve(base64data);
        };
      });
      blobPromise.then(value => {
        this.setState({ photo: value })
      });
    }
  };


  render() {
    const { files, uploadError, submitted, photo, loading, success } = this.state;

    if(loading) {
      return(
        <div className="body-background">
          <div className="centered">
            <BounceLoader
              sizeUnit={"px"}
              size={75}
              color={'rgb(37, 124, 191)'}
              loading={true}
            />
          </div>
        </div>
      )
    }

    if(success) {
      return(
        <div className="body-background">
          <div className="container col-12-sm w-p-20">
            <div className="login-card w-shadow">
              <div>
                <FontAwesomeIcon icon={faCheckCircle} color="#46ce23" size="4x"/>
                <h3 className="mt-4">Informacion Completada</h3>
                <p>Ahora vas a aparecer en el listado de medicos de Waldoc</p>
                <Link className="btn btn-primary custom-btn" to="/">{i18n.t('register.toHome')}</Link>
              </div>
            </div>
          </div>
        </div>

      )
    }

    const thumbsContainer = {
      width: "64px",
      height: "64px",
      borderRadius: "50%",
      objectFit: "cover",
      objectPosition: "center"
    };

    return (
      <div className="body-background">
        <div className="container col-12-sm w-p-20">
          <div className="login-card w-shadow">
            <h3>{i18n.t('register.completeProfTitle')}</h3>
            <div>
            </div>
            <form className="mb-4 mt-3">
              <label className={(submitted && !photo) ? 'text-danger' : ''}>{i18n.t(`register.photo`)}</label>
              <Dropzone
                accept="image/*"
                onDrop={(accepted, rejected) => this.onDrop(accepted, rejected)}
              >
                {({getRootProps, getInputProps}) => (
                  <section className="dropzone">
                    <div {...getRootProps()}>
                      <input {...getInputProps()} />
                      {
                        !uploadError ?
                          <p className="mb-0">{i18n.t('register.photoUpload')}</p>
                          :
                          <p className="mb-0 text-danger">{i18n.t('register.photoUploadError')}</p>
                      }
                    </div>
                  </section>
                )}
              </Dropzone>
              {
                files.length !== 0 &&
                  <div className="mb-3">
                    <img style={thumbsContainer} src={files[0].preview} alt="profile" />
                  </div>
              }
              {
                this.renderProfessionalForm()
              }
            </form>
            <div className="row container">
              <div onClick={() => this.handleSubmit()} className="btn btn-primary custom-btn pull-right">{i18n.t('register.me')}</div>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

const mapStateToProps = state => ({
  user: state.auth,
});

export default connect(mapStateToProps, null)(CompleteProfile);
