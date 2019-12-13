import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import BounceLoader from 'react-spinners/BounceLoader';
import { faCheckCircle, faTimesCircle } from '@fortawesome/free-solid-svg-icons';

import { ApiClient } from '../../../utils/apiClient';
import i18n from "../../../i18n";


class Confirm extends React.Component {
  constructor(props) {
    super(props);
    this.API = new ApiClient(props);
    this.state = {
      loading: true,
      success: false,
    }
  }

  componentDidMount() {
    const { token } = this.props.match.params;
    this.setState({ loading: true })
    this.API.get(`/patient/confirm?token=${token}`).then(response => {
      if(response.status >= 200) {
        this.setState({ success: true, loading: false });
      } else {
        this.setState({ success: false, loading: false });
      }
    })
      .catch(() => this.setState({ success: false, loading: false }));

  }

  render() {
    const { loading, success } = this.state;
    return (
      <div className="body-background">
        <div className="container col-12-sm w-p-20">
          <div className="login-card w-shadow">
            {
              loading &&
              <div className="center-horizontal p-5">
                <BounceLoader
                  sizeUnit={"px"}
                  size={75}
                  color={'rgb(37, 124, 191)'}
                  loading={true}
                />
              </div>
            }
            { !loading && !success &&
              <div>
                <FontAwesomeIcon icon={faTimesCircle} color="#bb0000" size="4x"/>
                <h3 className="mt-4">{i18n.t('register.problem')}</h3>
                <p>{i18n.t('register.notRegister')}</p>
                <Link className="btn btn-primary custom-btn" to="/">{i18n.t('register.toHome')}</Link>
              </div>
            }
            { !loading && success &&
            <div>
              <FontAwesomeIcon icon={faCheckCircle} color="#46ce23" size="4x"/>
              <h3 className="mt-4">{i18n.t('register.activatedAccount')}</h3>
              <p>{i18n.t('register.activatedAccessAccount')}</p>
              <Link className="btn btn-primary custom-btn" to="/">{i18n.t('register.toHome')}</Link>
            </div>
            }

          </div>
        </div>
      </div>
    )
  }
}

export default Confirm;
