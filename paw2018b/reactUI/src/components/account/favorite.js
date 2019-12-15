import React from 'react'
import { withRouter } from 'react-router-dom'
import PropTypes from 'prop-types'

class Favorite extends React.Component {
  static propTypes = {
    data: PropTypes.object.isRequired,
    history: PropTypes.object.isRequired,
  };

  handleClick() {
    const { id } = this.props.data;
    this.props.history.push(`/specialist/${id}`);
  }

  render() {
    const { firstName, lastName } = this.props.data;

    return(
      <div className="d-flex flex-row favorite-card" onClick={() => this.handleClick()}>
        <div className="card-text pt-3 pb-3">
          <h5 className="doctor-name mb-0 pl-3">{firstName} {lastName}</h5>
        </div>
      </div>
    );
  }
}

export default withRouter(Favorite);
