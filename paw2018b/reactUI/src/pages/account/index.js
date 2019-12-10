/**
 * Created by estebankramer on 14/10/2019.
 */
import React from 'react'
import BounceLoader from 'react-spinners/BounceLoader';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPhone, faMapMarker } from '@fortawesome/free-solid-svg-icons';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';

class Account extends React.Component {
  state = {
    loading: false,
    error: false,
  }

  render() {
    const { user } = this.props.user;

    if(!this.props.user.auth) {
      return(<Redirect to="/login"/>)

    }

    const { firstName, lastName } = user;

    return (
      <div className="body-background">
        <div className="main-container">
          <div className="container">
            <div className="pt-4 pb-3">
              <div className="login-card w-shadow flex-row">
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
