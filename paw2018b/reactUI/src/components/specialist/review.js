import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';

class Review extends React.Component {
  render() {
    const ar = [1,2,3,4];
    return(
      <div className="pt-1">
        <div className="row m-0 p-0 pb-2">
          {
            ar.map(() => <FontAwesomeIcon icon={faStar} />)
          }
        </div>
        <p className="mb-0">Nombre Apellido</p>
        <small className="w-text-light">Lorem ipsum dolor sit amet</small>
      </div>
    );
  }
}

export default Review;
