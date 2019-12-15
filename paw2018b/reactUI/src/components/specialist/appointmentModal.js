import React from 'react';
import Modal from 'react-bootstrap/Modal';
import BounceLoader from 'react-spinners/BounceLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimesCircle, faCheckCircle } from '@fortawesome/free-solid-svg-icons';
import DatePicker from 'react-datepicker';
import PropTypes from 'prop-types';
import i18n from "../../i18n";
import moment from 'moment';


class AppointmentModal extends React.Component {
  static propTypes = {
    modalVisible: PropTypes.bool.isRequired,
    submitted: PropTypes.bool.isRequired,
    appointmentLoading: PropTypes.bool.isRequired,
    loading: PropTypes.bool.isRequired,
    appointmentError: PropTypes.bool.isRequired,
    date: PropTypes.object.isRequired,
    firstDate: PropTypes.object.isRequired,
    time: PropTypes.object.isRequired,
    firstName: PropTypes.string.isRequired,
    excludedDates: PropTypes.array.isRequired,
    excludedTimes: PropTypes.array.isRequired,
    minAndMaxTimes: PropTypes.object.isRequired,
    addAppointment: PropTypes.func.isRequired,
    toggleModal: PropTypes.func.isRequired,
    onChange: PropTypes.func.isRequired,

  };


  render() {
    const { modalVisible, submitted, date, excludedDates, firstDate, minAndMaxTimes, time, appointmentLoading, loading,
      addAppointment, toggleModal, excludedTimes, appointmentError, firstName, onChange } = this.props

    return(
      <Modal
        show={modalVisible}
        onHide={() => toggleModal()}
        size="lg"
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title id="example-custom-modal-styling-title">
            {i18n.t('appointment.reserve')}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {
            !submitted &&
            <div>
              <strong>{i18n.t('appointment.selectDateTime')}</strong>
              <div className="row mt-2">
                <div className="col-sm-6">
                  <label className="mr-2">{i18n.t('appointment.date')}</label>
                  <DatePicker
                    selected={date}
                    onChange={date => onChange(date, 'date')}
                    excludeDates={excludedDates}
                    minDate={firstDate}
                    maxDate={moment(firstDate).add(40, 'days').toDate()}
                  />
                </div>
                <div className="col-sm-6 pl-0">
                  <label className="mr-2">{i18n.t('appointment.time')}</label>
                  <DatePicker
                    selected={time}
                    onChange={date => onChange(date, 'time')}
                    minTime={minAndMaxTimes.min}
                    maxTime={minAndMaxTimes.max}
                    excludeTimes={excludedTimes}
                    showTimeSelect
                    showTimeSelectOnly
                    timeIntervals={30}
                    timeCaption={i18n.t('appointment.time')}
                    dateFormat="h:mm aa"
                  />
                </div>
              </div>
            </div>
          }
          {
            submitted && appointmentLoading &&
            <div className="center-horizontal p-5">
              <BounceLoader
                sizeUnit={"px"}
                size={75}
                color={'rgb(37, 124, 191)'}
                loading={true}
              />
            </div>
          }
          {
            submitted && appointmentError && !appointmentLoading &&
            <div>
              <FontAwesomeIcon icon={faTimesCircle} color="#bb0000" size="4x"/>
              <h3 className="mt-4">{i18n.t('error.problem')}</h3>
              <p className="mb-0">{i18n.t('appointment.error')}</p>
            </div>
          }
          {
            submitted && !appointmentError && !appointmentLoading &&
            <div>
              <FontAwesomeIcon icon={faCheckCircle} color="#46ce23" size="4x"/>
              <h3 className="mt-4">{i18n.t('appointment.reserved')}</h3>
              <p className="mb-0">{firstName} te espera el {moment(date).format('DD/MM')} a las {moment(time).format('HH:mm')}hs.</p>
            </div>
          }
        </Modal.Body>
        {
          !submitted &&
          <Modal.Footer>
            <button className="btn btn-success" onClick={() => addAppointment()}>
              {i18n.t('appointment.reserve')}
            </button>
          </Modal.Footer>
        }
        {
          submitted && !loading &&
          <Modal.Footer>
            <button className="btn btn-secondary" onClick={() => toggleModal()}>
              {i18n.t('appointment.close')}
            </button>
          </Modal.Footer>
        }
      </Modal>
    );
  }
}

export default AppointmentModal;
