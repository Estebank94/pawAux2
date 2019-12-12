/**
 * Created by estebankramer on 14/10/2019.
 */
import React from 'react'
import PulseLoader from 'react-spinners/PulseLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPhone, faMapMarker } from '@fortawesome/free-solid-svg-icons';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import { ApiClient } from '../../utils/apiClient';
import Tabs from 'react-bootstrap/Tabs'
import Tab from 'react-bootstrap/Tab';
import Favorite from '../../components/account/favorite';
import Appointment from '../../components/account/appointment';

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

  render() {
    const { user } = this.props.user;
    const { loading, personal } = this.state;

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
                      {/*<img className="avatar big" src={`data:image/jpeg;base64,${profilePicture}`} />*/}
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
                      <Tabs defaultActiveKey="1">
                        <Tab eventKey="1" title="Turnos pendientes">
                          {
                            personal.futureAppointments
                              .map((appointment, index) =>
                                <Appointment
                                  key={index}
                                  data={appointment}
                                  cancel={this.cancelAppointment}
                                  cancelable
                                />)
                          }
                        </Tab>
                        <Tab eventKey="2" title="Historial de turnos">
                          {
                            personal.historicalAppointments
                              .map((appointment, index) =>
                                <Appointment
                                  key={index}
                                  data={appointment}
                                />)
                          }
                        </Tab>
                        <Tab eventKey="3" title="Especialistas favoritos">
                          {
                            personal.favorites.map((favorite, index) => <Favorite key={index} data={favorite.doctor} />)
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
