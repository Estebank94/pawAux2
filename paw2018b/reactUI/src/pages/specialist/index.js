/**
 * Created by estebankramer on 14/10/2019.
 */
import React from 'react'
import BounceLoader from 'react-spinners/BounceLoader';
import PulseLoader from 'react-spinners/PulseLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPhone, faMapMarker, faHeart, faCalendarPlus } from '@fortawesome/free-solid-svg-icons';
import Review from '../../components/specialist/review';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { connect } from 'react-redux';
import Modal from 'react-bootstrap/Modal';
import { ApiClient } from '../../utils/apiClient';
import DatePicker from 'react-datepicker';
import moment from 'moment';

import "react-datepicker/dist/react-datepicker.css";


class Specialist extends React.Component {
    constructor(props){
      super(props);
      this.API = new ApiClient(props);
      this.state = {
        loading: true,
        error: false,
        specialist: null,
        favorite: null,
        review: '',
        modalVisible: false,
        date: null,
        time: null,
        excludedDates: null,
        firstDate: new Date(),
        futures: null,
        excludedTimes: null,
        selectedWorkingHour: null,
        minAndMaxTimes: null,
      }
    }


    componentDidMount() {
      const { id } = this.props.match.params;
      this.API.get('/doctor/' + id)
        .then(async response => {
          await this.setState({ specialist: response.data });

          this.API.get('/doctor/' + id +'/futures').then(async response => {

            const futures = response.data.futures;
            const excludedDates = this.calculateExcludedDates()
            const firstDate = this.calculateFirstDate();

            await this.setState({
              futures,
              excludedDates,
              firstDate,
              date: firstDate
            })

            const excludedTimes = this.calculateExcludedTimes();
            const selectedWorkingHour = this.calculateSelectedWorkingHour();

            await this.setState({
              excludedTimes,
              selectedWorkingHour,
            })

            const minAndMaxTimes = this.calculateMinAndMaxTimes();

            await this.setState({
              minAndMaxTimes,
              time: this.roundTime(minAndMaxTimes.min)
            })

            if(this.props.user) {
              this.API.get('/patient/personal').then(response => {
                const filtered = response.data.favorites.filter(favorite => favorite.doctor.id === parseInt(id));
                if(filtered.length > 0){
                  this.setState({ favorite: true })
                } else {
                  this.setState({ favorite: false })
                }
              })
            }
            this.setState({ loading: false })
          })
        })
        .catch(() => this.setState({ loading: false, error: true }));
    }

  handleChange(e) {
    e.preventDefault();
    const { name, value } = e.target;
    this.setState({[name]: value });
  }

  addToFavorites() {
    this.API.put(`doctor/${this.state.specialist.id}/favorite/add`).then(response => {
      if(response.status >= 200) {
        this.setState({ favorite: true })
      }
    })
  }

  removeFromFavorites() {
    this.API.put(`doctor/${this.state.specialist.id}/favorite/remove`).then(response => {
      if(response.status >= 200) {
        this.setState({ favorite: false });
      }
    })
  }

  renderFavoriteButton(favorite) {
    if(favorite === null) {
      return(
        <div className="btn btn-primary custom-btn mt-2">
            <PulseLoader
              size={'30px'}
              color={'#FFF'}
              loading={true}
            />
        </div>)
    }

    if(favorite) {
      return(
        <div className="btn btn-primary custom-btn mt-2" onClick={() => this.removeFromFavorites()}>
          <FontAwesomeIcon className="mr-2" icon={faHeart} style={{ color: '#b52e2e' }} /> Eliminar de favoritos
        </div>
      )
    }

    return(
      <div className="btn btn-primary custom-btn mt-2" onClick={() => this.addToFavorites()}>
        <FontAwesomeIcon className="mr-2" icon={faHeart} style={{ color: '#FFF' }} /> Agregar a favoritos
      </div>
    )
  }

  toggleModal = () => {
    this.setState(prevState => ({
      modalVisible: !prevState.modalVisible
    }));
  }

  onChange = async (date, name) => {
    await this.setState({ [name]: date })
    if(name === 'date') {
      const excludedTimes = this.calculateExcludedTimes();
      const selectedWorkingHour = this.calculateSelectedWorkingHour();

      await this.setState({
        excludedTimes,
        selectedWorkingHour,
      })

      const minAndMaxTimes = this.calculateMinAndMaxTimes();

      await this.setState({
        minAndMaxTimes,
        time: this.roundTime(minAndMaxTimes.min)
      })
    }
  }

  calculateExcludedDates = () => {
      const workingHours = this.state.specialist.workingHours;
      let excludedDates = [];

      for(let i = 0; i < 45; i++) {
        const day = moment().add(i, 'days');
        workingHours.map(wh => {
          if(wh.dayOfWeek !== day.isoWeekday()){
            excludedDates.push(day.toDate())
          }})
      }
      return excludedDates;
  }

  calculateFirstDate = () => {
    const workingHours = this.state.specialist.workingHours;

    let min = workingHours[0].dayOfWeek;
    workingHours.map(wh => {
      if(wh.dayOfWeek < min) {
        min = wh.dayOfWeek;
      }
    })

    return moment().isoWeekday(min).toDate();
  }

  calculateExcludedTimes = () => {
    const { date, futures } = this.state;
    const excluded = [];
    if(futures) {
      futures.map(f => {
        if(moment(date).isSame(f.day)) {
          f.times.map(time => excluded.push(moment(time, 'HH:mm').toDate()))
        }
      })
    }
    return excluded
  }

  calculateSelectedWorkingHour = () => {
      const { date, specialist } = this.state;
      const workingHours = specialist.workingHours;
      return workingHours.reduce(wh => {
        return (wh.dayOfWeek === moment(date).isoWeekday());
      })
  }

  calculateMinAndMaxTimes = () => {
      const { selectedWorkingHour } = this.state;
      console.log(selectedWorkingHour, 'sel');
      let min, max;

      if(moment(selectedWorkingHour.startTime, 'HH:mm:ss').isBefore(moment())) {
        min = moment().toDate();
      } else {
        min = moment(selectedWorkingHour.startTime, 'HH:mm:ss').toDate();
      }
      max = moment(selectedWorkingHour.finishTime, 'HH:mm:ss').toDate();

    return { min, max }
  }

  roundTime = (time) => {
    const start = moment(time);
    const remainder = 30 - (start.minute() % 30);
    return moment(start).add(remainder, "minutes").toDate();
  }

  addAppointment() {
    const { id } = this.props.match.params;
    const { date, time } = this.state;
    const formattedDate = moment(date).format('YYYY-MM-DD')
    const formattedTime = moment(time).format('HH:mm')
    console.log('formated', formattedDate, formattedTime)
    this.API.put(`doctor/${id}/appointment/add`, { day: formattedDate, time: formattedTime }).then(response => console.log(response));
  }


  render() {
    const { error, loading, specialist, review, favorite, modalVisible, time, excludedDates, firstDate,
      excludedTimes, date, minAndMaxTimes } = this.state;

    if(loading) {
      return (
        <div className="centered">
          <BounceLoader
            sizeUnit={"px"}
            size={75}
            color={'rgb(37, 124, 191)'}
            loading={true}
          />
        </div>
      )
    }

    if(error) {
      return (
        <p>Hubo un error!</p>
      )
    }

    const { address, averageRating, district, firstName, id, insurances, lastName, phoneNumber, profilePicture,
      reviews, sex, specialties, workingHours } = specialist;

    return (
      <div className="body-background">
        <Modal
          show={modalVisible}
          onHide={() => this.toggleModal()}
          size="lg"
          centered
        >
          <Modal.Header closeButton>
            <Modal.Title id="example-custom-modal-styling-title">
              Reservar Turno
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <strong>Seleccionar una fecha y horario disponible</strong>
            <div className="row mt-2">
              <div className="col-sm-6">
                <label className="mr-2">Fecha</label>
                <DatePicker
                  selected={date}
                  onChange={date => this.onChange(date, 'date')}
                  excludeDates={excludedDates}
                  minDate={firstDate}
                  maxDate={moment(firstDate).add(40, 'days').toDate()}
                />
              </div>
              <div className="col-sm-6 pl-0">
                <label className="mr-2">Horario</label>
                <DatePicker
                  selected={time}
                  onChange={date => this.onChange(date, 'time')}
                  minTime={minAndMaxTimes.min}
                  maxTime={minAndMaxTimes.max}
                  excludeTimes={excludedTimes}
                  showTimeSelect
                  showTimeSelectOnly
                  timeIntervals={30}
                  timeCaption="Horario"
                  dateFormat="h:mm aa"
                />
              </div>
            </div>
          </Modal.Body>
          <Modal.Footer>
            <button className="btn btn-success" onClick={() => this.addAppointment()}>
              Reservar Turno
            </button>
          </Modal.Footer>
        </Modal>
        <div className="main-container">
          <div className="container pt-4">
            <div className="login-card w-shadow flex-row">
              <div className="card-body">
                <div className="card-text">
                  <div className="row">
                    <img className="avatar big" src={`data:image/jpeg;base64,${profilePicture}`} />
                    <div className="doctor-info-container">
                      <div>
                        <div className="row center-vertical">
                          <h3 className="doctor-name" style={{marginLeft: 14 }}>{firstName} {lastName}</h3>
                        </div>
                        <p className="doctor-specialty" style={{ paddingRight: 20 }}>{specialties.map(s => s+ ' ')}</p>
                        <p className="doctor-text"><FontAwesomeIcon className="mr-2" icon={faPhone} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{phoneNumber}</p>
                        <p className="doctor-text"><FontAwesomeIcon className="mr-2" icon={faMapMarker} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{address}, CABA</p>
                        <div>
                          <div className="btn btn-success mt-2 mr-2" onClick={() => this.toggleModal()}>
                            <FontAwesomeIcon className="mr-2" icon={faCalendarPlus} style={{ color: '#FFF' }} /> Reservar Turno
                          </div>
                          {this.renderFavoriteButton(favorite)}
                        </div>

                      </div>
                    </div>
                  </div>
                  <hr />
                  <h3>Descripción</h3>
                  <h5>Prepagas Medicas</h5>
                  <Row>
                    {
                      insurances.map((insurance, index) => {
                        return(
                          <Col key={index}>
                            <p className="font-weight-bold w-list-title mb-0">{insurance.name}</p>
                            {
                              insurance.plans.map((plan, index)=> <li key={index}>{plan}</li>)
                            }
                          </Col>
                        )
                      })
                    }
                  </Row>
                  <hr />
                  <h3>Reseñas</h3>
                  <Review />
                  <h4 className="mt-3">Dejá tu reseña</h4>
                  <textarea name="review" value={review} type="text" rows="3" className={'form-control'}  placeholder="Ingresa tu reseña" onChange={(e) =>this.handleChange(e)}/>
                  <div className="btn btn-primary custom-btn mt-2">Dejar reseña</div>
                </div>
              </div>
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

export default connect(mapStateToProps, null)(Specialist);