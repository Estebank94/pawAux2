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
import DatePicker from 'react-date-picker';
import { ApiClient } from '../../utils/apiClient';

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
        date: new Date(),
        time: '',

      }
    }


    componentDidMount() {
      const { id } = this.props.match.params;
      this.API.get('/doctor/' + id)
        .then(response => {
          this.setState({loading: false, specialist: response.data });
        })
        .catch(() => this.setState({ loading: false, error: true }));

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
    this.setState(prevState => ({    // prevState?
      modalVisible: !prevState.modalVisible
    }));
  }

  onChange = date => this.setState({ date })

  handleChange(e) {
    e.preventDefault();
    const { name, value } = e.target;
    this.setState({[name]: value })
  }


  render() {
    const { error, loading, specialist, review, favorite, modalVisible } = this.state;

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
      reviews, sex, specialties, workingHours, time } = specialist;
    console.log(specialist);

    return (
      <div className="body-background">
        <Modal
          show={modalVisible}
          onHide={() => this.toggleModal()}
          dialogClassName="modal-90w"
          aria-labelledby="example-custom-modal-styling-title"
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
                <label>Fecha</label>
                <div>
                  <DatePicker
                    onChange={this.onChange}
                    value={this.state.date}
                    minDate={new Date()}
                    className="no-borders mb-3"
                    calendarIcon={null}
                    clearIcon={null}
                  />
                </div>
              </div>
              <div className="col-sm-6 pl-0">
                <label>Horario</label>
                <select name="time" value={time} className={'form-control'} onChange={(e) =>this.handleChange(e)}>
                  <option value="">Seleccionar un horario</option>
                  <option value="10:00">10:00</option>
                </select>
              </div>
            </div>
          </Modal.Body>
          <Modal.Footer>
            <button className="btn btn-success" onClick={this.toggleModal}>
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