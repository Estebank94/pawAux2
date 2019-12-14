/**
 * Created by estebankramer on 14/10/2019.
 */
import React from 'react'
import PulseLoader from 'react-spinners/PulseLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart, faCalendar, faHistory, faUserMd } from '@fortawesome/free-solid-svg-icons';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import { ApiClient } from '../../utils/apiClient';
import Tabs from 'react-bootstrap/Tabs'
import Tab from 'react-bootstrap/Tab';
import Favorite from '../../components/account/favorite';
import Appointment from '../../components/account/appointment';
import DoctorAppointment from '../../components/account/doctorAppointment';
import i18n from "../../i18n";

class Account extends React.Component {
  constructor(props){
    super(props);
    this.API = new ApiClient(props);
    this.state = {
      loading: true,
      personal: null,
      error: false,
    }
  }

  componentDidMount(){
    this.setState({ loading: true });
    this.API.get('/patient/personal').then(response => {
      if(response.status >= 200) {
        this.setState({ personal: response.data, loading: false });
      } else {
        this.setState({ error: true, loading: false });
      }
    })
  }

  cancelAppointment = (appointment) => {
    const id = appointment.doctor.id
    this.API.put(`doctor/${id}/appointment/cancel`, { day: appointment.appointmentDay, time: appointment.appointmentTime })
  }

  cancelPatientAppointment = (appointment) => {
    const id = appointment.patient.id
    this.API.put(`patient/${id}/appointment/cancel`, { day: appointment.appointmentDay, time: appointment.appointmentTime })
  }

  noResults(tab) {
    return(
      <h5 className="p-5 center-horizontal w-text-light mb-0">{i18n.t('account.noResults')} {tab}</h5>
    )
  }

  render() {
    const { user } = this.props.user;
    const { loading, personal } = this.state;

    console.log(user)

    if(!this.props.user.auth) {
      return(<Redirect to="/login"/>)
    }

    const { firstName, lastName } = user;

    return (
      <div className="body-background">
        <div className="main-container">
          <div className="container">
            <div className="pt-4 pb-3">
              <div className="login-card p-3 w-shadow flex-row">
                <div className="card-body">
                  <div className="card-text">
                    <div className="row">
                      <div className="doctor-info-container">
                        <div>
                          <div className="row center-vertical">
                            <h3 className="doctor-name" style={{marginLeft: 14 }}>{firstName} {lastName}</h3>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  {
                    !loading ?
                      <Tabs defaultActiveKey={user.doctor ? 0 : 1}>
                        {
                          personal.doctorInformation &&
                          <Tab eventKey="0" title={<span><FontAwesomeIcon className="mr-2 blue-icon" icon={faUserMd}  />{i18n.t('account.patientAppointments')}</span>}>
                            {
                              personal.doctorInformation.futureAppointments.length > 0 ?
                                personal.doctorInformation.futureAppointments
                                  .map((appointment, index) =>
                                    <DoctorAppointment
                                      key={index}
                                      data={appointment}
                                      cancel={this.cancelPatientAppointment}
                                      cancelable
                                    />)
                                :
                                this.noResults(i18n.t('account.patientAppointments'))
                            }
                          </Tab>
                        }
                        <Tab eventKey="1" title={<span><FontAwesomeIcon className="mr-2 blue-icon" icon={faCalendar}  />{i18n.t('appointment.futureTitle')}</span>}>
                          {
                            personal.futureAppointments.length > 0 ?
                              personal.futureAppointments
                                .map((appointment, index) =>
                                  <Appointment
                                    key={index}
                                    data={appointment}
                                    cancel={this.cancelAppointment}
                                    cancelable
                                  />)
                              :
                              this.noResults(i18n.t('appointment.futureTitle'))
                          }
                        </Tab>
                        <Tab eventKey="2" title={<span><FontAwesomeIcon className="mr-2 blue-icon" icon={faHistory}  />{i18n.t('appointment.historicalTitle')}</span>}>
                          {
                            personal.historicalAppointments.length > 0 ?
                              personal.historicalAppointments
                                .map((appointment, index) =>
                                  <Appointment
                                    key={index}
                                    data={appointment}
                                  />)
                              :
                              this.noResults(i18n.t('appointment.historicalTitle'))
                          }
                        </Tab>
                        <Tab eventKey="3" title={<span><FontAwesomeIcon className="mr-2 blue-icon" icon={faHeart}  />{i18n.t('specialist.favoriteTitle')}</span>}>
                          {
                            personal.favorites.length > 0 ?
                              personal.favorites.map((favorite, index) => <Favorite key={index} data={favorite.doctor} />)
                              :
                              this.noResults(i18n.t('specialist.favoriteTitle'))
                          }
                        </Tab>
                      </Tabs>
                      :
                      <div className="center-horizontal">
                        <PulseLoader
                          sizeUnit={"px"}
                          size={18}
                          color={'#d1d1d1'}
                          loading={true}
                        />
                      </div>
                  }
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

export default connect(mapStateToProps, null)(Account);
