/**
 * Created by estebankramer on 14/10/2019.
 */
import React from 'react'
import BounceLoader from 'react-spinners/BounceLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPhone, faMapMarker } from '@fortawesome/free-solid-svg-icons';

import fetchApi from '../../utils/api'

class Specialist extends React.Component {
    state = {
        loading: true,
        error: false,
        specialist: null,
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
    render() {
        const { error, loading, specialist } = this.state;

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
                    <div className="container">
                        <div className="pt-4 pb-3">
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
                                                    <p className="doctor-specialty" style={{ paddingRight: 20 }}>{specialties.specialties.map(s => s.speciality + ' ')}</p>
                                                    <p className="doctor-text"><FontAwesomeIcon className="mr-2" icon={faPhone} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{phoneNumber}</p>
                                                    <p className="doctor-text"><FontAwesomeIcon className="mr-2" icon={faMapMarker} style={{ color: 'rgba(37, 124, 191, 0.5)' }} />{address}, CABA</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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