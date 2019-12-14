import React from 'react'
import { withRouter } from 'react-router-dom'
import i18n from "../../i18n";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPhone, faMapMarker } from '@fortawesome/free-solid-svg-icons';
import PropTypes from 'prop-types';

class SpecialistCard extends React.Component {
  static propTypes = {
    data: PropTypes.object.isRequired,
    history: PropTypes.object.isRequired
  };

  handleClick() {
    const { id } = this.props.data;
    this.props.history.push(`/specialist/${id}`);
  }

  render() {
    const { profilePicture, firstName, lastName, specialties, address, phoneNumber } = this.props.data;
    return(
      <div className="card-doctor d-flex flex-row box appointment-card" onClick={() => this.handleClick()}>
        <img className="avatar big" src={`data:image/jpeg;base64,${profilePicture}`} />
        <div className="card-body">
          <div className="card-text">
            <h3 className="doctor-name">{firstName} {lastName}</h3>
            <div className="row container">
              <p className="doctor-specialty" style={{ paddingRight: 20 }}>{specialties.map(s => s + ' ')}</p>
            </div>
            <p className="doctor-text"><FontAwesomeIcon className="mr-2" icon={faPhone} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{phoneNumber}</p>
            <p className="doctor-text"><FontAwesomeIcon className="mr-2" icon={faMapMarker} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{address}{i18n.t('specialist.city')}</p>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(SpecialistCard);
