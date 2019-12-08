/**
 * Created by estebankramer on 14/10/2019.
 */
import React from 'react'
import BounceLoader from 'react-spinners/BounceLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPhone, faMapMarker } from '@fortawesome/free-solid-svg-icons';
import Review from '../../components/specialist/review'

import fetchApi from '../../utils/api'

class Specialist extends React.Component {
    state = {
        loading: true,
        error: false,
        specialist: null,
        review: '',
    }

    componentDidMount() {
        const { id } = this.props.match.params;
        fetchApi('/doctor/' + id, 'GET')
            .then(specialist => {
                console.log(specialist);
                this.setState({loading: false, specialist });
            })
            .catch(() => this.setState({ loading: false, error: true }));
    }

  handleChange(e) {
    e.preventDefault();
    const { name, value } = e.target;
    this.setState({[name]: value });
  }

    render() {
        const { error, loading, specialist, review } = this.state;

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

        const { address, averageRating, district, firstName, id, insurancesPlans, lastName, phoneNumber, profilePicture,
        reviews, sex, specialties, workingHours } = specialist;
        console.log(specialist);

        return (
            <div className="body-background">
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
                                            </div>
                                        </div>
                                    </div>
                                    <hr />
                                    <h3>Reseñas</h3>
                                    <Review />
                                    <h4 className="mt-3">Dejá tu reseña</h4>
                                    <textarea name="review" value={review} type="text" rows="3" className={'form-control'}  placeholder="Ingresa tu reseña" onChange={(e) =>this.handleChange(e)}/>
                                    <span className="btn btn-primary custom-btn mt-2">Dejar reseña</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Specialist;