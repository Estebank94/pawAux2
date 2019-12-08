import React from 'react'
import { withRouter } from 'react-router-dom'

class SpecialistCard extends React.Component {

    renderRating = () => {
        const { averageRating } = this.props.data;
        let stars = [];

        // Outer loop to create parent
        for (let i = 0; i < averageRating; i++) {
            stars.push(<i className="fas fa-star star-yellow"></i>);
        }
        for(let j = averageRating + 1; j < 5; j++){
            stars.push(<i className="fas fa-star star-grey"></i>)
        }
        return stars;
    }

    handleClick() {
      const { id } = this.props.data;
      this.props.history.push(`/specialist/${id}`);
    }

    render() {
      const { profilePicture, firstName, lastName, phoneNumber, specialties, averageRating, address } = this.props.data;
    return(
      <div className="card-doctor d-flex flex-row box" onClick={() => this.handleClick()}>
          <img class="avatar big" src={`data:image/jpeg;base64,${profilePicture}`} className="avatar" />
          <div className="card-body">
              <div className="card-text">
                  <h3 className="doctor-name">{firstName} {lastName}</h3>
                  <div className="row container">
                      <p className="doctor-specialty" style={{ paddingRight: 20 }}>{specialties.map(s => s + ' ')}</p>
                  </div>
                  <p className="doctor-text">Certificado</p>
                  { averageRating !== 0 &&
                      <div className="row container">
                          {this.renderRating()}
                      </div>
                  }
                  <p className="doctor-text">"Muy buena atención, muy puntual"</p>
                  <p className="doctor-text"><i className="far fa-clock"></i>8 - 20pm</p>
                  <p className="doctor-text"><i className="fas fa-map-marker-alt"></i>{address}, CABA</p>
              </div>
          </div>
      </div>
    );
  }
}

export default withRouter(SpecialistCard);
