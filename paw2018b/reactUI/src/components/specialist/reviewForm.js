import React from 'react';
import { withRouter } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLock, faCheck } from '@fortawesome/free-solid-svg-icons';
import PropTypes from 'prop-types';
import i18n from "../../i18n";


class ReviewForm extends React.Component {
  static propTypes = {
    canReview: PropTypes.bool.isRequired,
    isAuthenticated: PropTypes.bool.isRequired,
    submit: PropTypes.func.isRequired
  };

  state = {
    submitted: false,
    canReview: false,
    isAuthenticated: false,
    description: '',
    stars: ''
  }

  componentWillReceiveProps(nextProps) {
    const canReview = nextProps.canReview;
    const isAuthenticated = nextProps.isAuthenticated;
    this.setState({ canReview, isAuthenticated });
  }

  submitReview = () => {
    const { stars, description } = this.state;
    this.setState({ submitted: true })
    this.props.submit({ stars, description })
  }

  handleChange(e) {
    e.preventDefault();
    const { name, value } = e.target;
    this.setState({[name]: value });
  }

  render() {
    const { submitted, canReview, isAuthenticated, description, stars } = this.state

    if(submitted) {
      return(
        <div className="mt-3">
          <div className="alert alert-success">
            <FontAwesomeIcon className="mr-2" icon={faCheck} style={{ color: 'rgba(0,0,0, 0.5)' }} />{i18n.t('review.thanks')}
          </div>
        </div>
      )
    }

    if(!isAuthenticated) {
      return(
        <div className="mt-3">
          <div className="alert alert-secondary" role="alert">
            <FontAwesomeIcon className="mr-2" icon={faLock} style={{ color: 'rgba(0,0,0, 0.5)' }} />{i18n.t('review.register')}
          </div>
        </div>
      )
    }

    if(!canReview) {
      return(
        <div className="mt-3">
          <div className="alert alert-secondary" role="alert">
            <FontAwesomeIcon className="mr-2" icon={faLock} style={{ color: 'rgba(0,0,0, 0.5)' }} />{i18n.t('review.appointment')}
          </div>
        </div>
      )
    }


    return(
      <div>
        <div className="form-group">
          <label>{i18n.t('review.stars')}</label>
          <select className="form-control" name="stars" value={stars} onChange={(e) =>this.handleChange(e)}>
            <option value="">{i18n.t('review.starsOption')}</option>
            <option value="1">⭐️</option>
            <option value="2">⭐️⭐️</option>
            <option value="3">⭐️⭐️⭐️</option>
            <option value="4">⭐️⭐️⭐️⭐️</option>
            <option value="5">⭐️⭐️⭐️⭐️⭐️</option>
          </select>
        </div>
        <label>{i18n.t('review.comments')}</label>
        <textarea name="description" value={description} type="text" rows="3" className={'form-control'}  placeholder={i18n.t('review.placeHolderComments')} onChange={(e) =>this.handleChange(e)}/>
        <div className="btn btn-primary custom-btn mt-3" onClick={() => this.submitReview()}>{i18n.t('review.button')}</div>
      </div>
    );
  }
}

export default withRouter(ReviewForm);
