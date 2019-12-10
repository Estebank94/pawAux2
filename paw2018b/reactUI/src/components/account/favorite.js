import React from 'react'
import { withRouter } from 'react-router-dom'

class Favorite extends React.Component {

  handleClick() {
    const { id } = this.props.data;
    this.props.history.push(`/specialist/${id}`);
  }

  render() {
    const { profilePicture, firstName, lastName } = this.props.data;
    return(
      <div className="d-flex flex-row favorite-card" onClick={() => this.handleClick()}>
        <img className="avatar" src={`data:image/jpeg;base64,${profilePicture}`} />
        <div className="d-flex align-items-center">
          <div className="card-text">
            <h3 className="doctor-name mb-0 pl-3">{firstName} {lastName}</h3>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(Favorite);
