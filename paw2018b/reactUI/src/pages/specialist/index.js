/**
 * Created by estebankramer on 14/10/2019.
 */
import React from 'react'
import BounceLoader from 'react-spinners/BounceLoader';
import PulseLoader from 'react-spinners/PulseLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPhone, faMapMarker, faHeart, faCalendarPlus, faLock, faGraduationCap, faLanguage, faUniversity } from '@fortawesome/free-solid-svg-icons';
import ReviewCard from '../../components/specialist/reviewCard';
import ReviewForm from '../../components/specialist/reviewForm';
import AppointmentModal from '../../components/specialist/appointmentModal';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { connect } from 'react-redux';
import { ApiClient } from '../../utils/apiClient';
import moment from 'moment';

import "react-datepicker/dist/react-datepicker.css";
import i18n from "../../i18n";


class Specialist extends React.Component {
    constructor(props){
      super(props);
      this.API = new ApiClient(props);
      this.state = {
        loading: true,
        error: false,
        specialist: null,
        favorite: null,
        reviews: null,
        modalVisible: false,
        date: null,
        time: null,
        excludedDates: null,
        firstDate: new Date(),
        futures: null,
        excludedTimes: null,
        selectedWorkingHour: null,
        minAndMaxTimes: null,
        submitted: false,
        appointmentError: false,
        appointmentLoading: false,
        pastAppointments: [],
        stars: '',
        description : '',
        appointmentId: '',
        reviewSubmitted: false,
        canReview: false,
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

            this.API.get('/doctor/' + id +'/reviews').then(response => {
              this.setState({ reviews: response.data.reviews });

              if(this.props.user.auth) {
                this.API.get('/patient/personal').then(response => {
                  this.setState({ pastAppointments: response.data.historicalAppointments })
                  const filtered = response.data.favorites.filter(favorite => favorite.doctor.id === parseInt(id));
                  if(filtered.length > 0){
                    this.setState({ favorite: true })
                  } else {
                    this.setState({ favorite: false })
                  }

                  this.API.get(`doctor/${id}/canReview`).then(response => {
                    this.setState({ canReview: response.data.canReview })

                  })
                })
              }
            })

            this.setState({ loading: false })
          })
        })
        .catch(() => this.setState({ loading: false, error: true }));
    }

    calculateAppointments() {
      const { id } = this.props.match.params;
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
      })
    }

  addToFavorites() {
    this.setState({ favorite: null })
    this.API.put(`doctor/${this.state.specialist.id}/favorite/add`).then(response => {
      if(response.status >= 200) {
        this.setState({ favorite: true })
      }
    })
  }

  removeFromFavorites() {
    this.setState({ favorite: null })
    this.API.put(`doctor/${this.state.specialist.id}/favorite/remove`).then(response => {
      if(response.status >= 200) {
        this.setState({ favorite: false });
      }
    })
  }

  renderFavoriteButton(favorite) {
    if(favorite === null) {
      return(
        <div className="btn btn-primary custom-btn mt-2 fav-button">
            <PulseLoader
              sizeUnit={"px"}
              size={10}
              color={'#FFF'}
              loading={true}
            />
        </div>)
    }

    if(favorite) {
      return(
        <div className="btn btn-primary custom-btn mt-2 fav-button" onClick={() => this.removeFromFavorites()}>
          <FontAwesomeIcon className="mr-2" icon={faHeart} style={{ color: '#b52e2e' }} /> {i18n.t('favorite.remove')}
        </div>
      )
    }

    return(
      <div className="btn btn-primary custom-btn mt-2 fav-button" onClick={() => this.addToFavorites()}>
        <FontAwesomeIcon className="mr-2" icon={faHeart} style={{ color: '#FFF' }} /> {i18n.t('favorite.add')}
      </div>
    )
  }

  toggleModal = () => {
      if(this.state.submitted) {
        this.calculateAppointments();
      }

      this.setState(prevState => ({
        modalVisible: !prevState.modalVisible,
        submitted: false
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

    let date = moment().isoWeekday(min);

    if(moment().isoWeekday(min).isBefore(moment())) {
      date.add(1, 'week')
    }
    return date.toDate();
  }

  calculateExcludedTimes = () => {
    const { date, futures } = this.state;
    let excluded = [];
    if(futures) {
      futures.map(f => {
        if(moment(date).isSame(f.day, 'day')) {
          f.times.map(time => {
            excluded.push(moment(time, 'HH:mm').toDate())
          })
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
      const { selectedWorkingHour, date, excludedTimes } = this.state;

      let min, max;

      if(moment(date).isSame(moment(), 'day') && moment(selectedWorkingHour.startTime, 'HH:mm:ss').isBefore(moment())) {
        min = moment().toDate();
      } else {
        min = moment(selectedWorkingHour.startTime, 'HH:mm:ss').toDate();
      }

      max = moment(selectedWorkingHour.finishTime, 'HH:mm:ss').toDate();

      excludedTimes.map(time => {
        if(moment(time).isSame(min)) {
          min = moment(min).add(30, 'minutes').toDate()
        }
        if(moment(time).isSame(max)) {
          max = moment(max).subtract(30, 'minutes').toDate()
        }
      })

    return { min, max }
  }

  roundTime = (time) => {
    const start = moment(time);
    const remainder = 30 - (start.minute() % 30);
    return moment(start).add(remainder, "minutes").toDate();
  }

  addAppointment = () => {
    const { id } = this.props.match.params;
    const { date, time } = this.state;
    const formattedDate = moment(date).format('YYYY-MM-DD')
    const formattedTime = moment(time).format('HH:mm')
    this.setState({ appointmentLoading: true, submitted: true })
    this.API.put(`doctor/${id}/appointment/add`, { day: formattedDate, time: formattedTime })
      .then(response => {
        if(response.status >= 200) {
          this.setState({ appointmentLoading: false, appointmentError: false })
        }
      })
      .catch(() => this.setState({ appointmentLoading: false, appointmentError: true }));
  }

  submitReview = (review) => {
    const { id } = this.props.match.params;
    this.API.post(`/doctor/${id}/makeReview`, { stars: review.stars, description: review.description }).then(response => console.log(response))
  }


  render() {
    const { error, loading, specialist, reviews, favorite, modalVisible, time, excludedDates, firstDate,
      excludedTimes, date, minAndMaxTimes, submitted, appointmentError, appointmentLoading, canReview } = this.state;

    if(loading) {
      return (
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

    if(error) {
      return (
        <p>{i18n.t('error.error')}</p>
      )
    }

    const { address, firstName, insurances, lastName, phoneNumber, profilePicture, specialties } = specialist;

    return (
      <div className="body-background">
        <AppointmentModal
          modalVisible={modalVisible}
          submitted={submitted}
          date={date}
          excludedDates={excludedDates}
          firstDate={firstDate}
          minAndMaxTimes={minAndMaxTimes}
          excludedtimes={excludedTimes}
          time={time}
          appointmentLoading={appointmentLoading}
          loading={loading}
          addAppointment={this.addAppointment}
          toggleModal={this.toggleModal}
          appointmentError={appointmentError}
          firstName={firstName}
          onChange={this.onChange}
        />
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
                        <p className="doctor-text"><FontAwesomeIcon className="mr-2" icon={faMapMarker} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{address}{i18n.t('specialist.city')}</p>
                        {
                          this.props.user.auth &&
                          <div>
                            <div className="btn btn-success mt-2 mr-2" onClick={() => this.toggleModal()}>
                              <FontAwesomeIcon className="mr-2" icon={faCalendarPlus} style={{ color: '#FFF' }} /> {i18n.t('appointment.reserve')}
                            </div>
                            {this.renderFavoriteButton(favorite)}
                          </div>
                        }
                        {
                          !this.props.user.auth &&
                          <div className="mt-3">
                            <div className="alert alert-secondary" role="alert">
                              <FontAwesomeIcon className="mr-2" icon={faLock} style={{ color: 'rgba(0,0,0, 0.5)' }} /> {i18n.t('appointment.register')}
                            </div>
                          </div>
                        }
                      </div>
                    </div>
                  </div>
                  <hr />
                  <h3>{i18n.t('specialist.description')}</h3>
                  <div><FontAwesomeIcon className="mr-2 description-icon" icon={faUniversity} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{specialist.description.certificate}</div>
                  <div><FontAwesomeIcon className="mr-2 description-icon" icon={faGraduationCap} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{specialist.description.education}</div>
                  <div><FontAwesomeIcon className="mr-2 description-icon" icon={faLanguage} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{specialist.description.languages}</div>
                  <h5 className="mt-3">{i18n.t('specialist.insurancesPlans')}</h5>
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
                  <h3>{i18n.t('review.reviewTitle')}</h3>
                  {
                    reviews &&
                    reviews.map((review, index) => <ReviewCard key={index} data={review} /> )
                  }
                  <h4 className="mt-3">{i18n.t('review.leaveReview')}</h4>
                  <ReviewForm canReview={canReview} isAuthenticated={!!this.props.user.auth} submit={this.submitReview} />
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