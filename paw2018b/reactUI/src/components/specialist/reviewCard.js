import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import PropTypes from 'prop-types';

class Review extends React.Component {
  static propTypes = {
    data: PropTypes.object.isRequired,
  };


  renderStars = (stars) => {
    let s = []
    for(let i = 0; i < stars; i++){
      s.push(<FontAwesomeIcon icon={faStar} className="w-star" />)
    }
    return s;
  }
  render() {
    const { reviewerFirstName, reviewerLastName, stars, description } = this.props.data
    return(
      <div className="pt-2 mb-3">
        <div className="row m-0 p-0 pb-2">
          {
            this.renderStars(stars)
          }
        </div>
        <p className="mb-0">{reviewerFirstName} {reviewerLastName}</p>
        <small className="w-text-light">{description}</small>
        <hr />
      </div>
    );
  }
}

export default Review;
