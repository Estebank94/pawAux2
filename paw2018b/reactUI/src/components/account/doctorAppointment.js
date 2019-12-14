import React from 'react';
import { withRouter } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPhone, faCalendar, faTrash } from '@fortawesome/free-solid-svg-icons';
import moment from 'moment';
import 'moment/locale/es'
import 'moment/locale/en-gb'
import PropTypes from 'prop-types';
import i18n from "../../i18n";


class DoctorAppointment extends React.Component {
  static propTypes = {
    cancel: PropTypes.func.isRequired,
    data: PropTypes.object,
  };

  state = {
    canceled: false
  }

  componentWillMount() {
    const lang = window.navigator.userLanguage || window.navigator.language;
    if(lang === 'en-US' || lang === 'en') {
      moment.locale('en-gb')
    } else {
      moment.locale('es')
    }
  }

  cancelAppointment() {
    this.setState({ canceled: true })
    this.props.cancel(this.props.data);
  }

  render() {
    const { appointmentDay, appointmentTime, patient } = this.props.data;
    const { firstName, lastName,  phoneNumber } = patient;

    if(this.state.canceled) {
      return null;
    }

    return(
      <div className="favorite-card appointment-card pt-3 pl-2 pr-2">
        <div>
          <h5 className="mb-2">{firstName} {lastName}</h5>
          <p className="mb-0"><FontAwesomeIcon className="mr-2" icon={faCalendar} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{moment(appointmentDay, 'YYYY-MM-DD').format('dddd DD MMMM')}{i18n.t('appointment.at')}{appointmentTime}hs.</p>
          <p className="mb-2"><FontAwesomeIcon className="mr-2" icon={faPhone} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{phoneNumber}</p>
        </div>
        <div className="btn btn-primary custom-btn" onClick={() => this.cancelAppointment()}><FontAwesomeIcon className="mr-2" icon={faTrash} style={{ color: '#FFF' }} /> {i18n.t('appointment.cancel')}</div>
      </div>
    );
  }
}

export default withRouter(DoctorAppointment);
