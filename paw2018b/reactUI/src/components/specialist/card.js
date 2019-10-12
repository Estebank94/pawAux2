import React from 'react'
import { Link } from 'react-router-dom';

class SpecialistCard extends React.Component {
  render() {
    return(
      <div className="card card-doctor d-flex flex-row box">
          <img src="https://d1k13df5m14swc.cloudfront.net/photos/Dr-Steven-Radowitz-MD-413-circle_medium__v1__.png" className="avatar" />
          <div className="card-body">
              <div className="card-text">
                  <h3 className="doctor-name">Nombre Apellido</h3>
                  <div className="row container">
                      <p className="doctor-specialty" style={{ paddingRight: 20 }}>Especialidades</p>
                  </div>
                  <p className="doctor-text">Certificado</p>
                  <div className="row container">
                      <i className="fas fa-star star-yellow"></i>
                      <i className="fas fa-star star-yellow"></i>
                      <i className="fas fa-star star-yellow"></i>
                      <i className="fas fa-star star-yellow"></i>
                      <i className="fas fa-star star-grey"></i>
                  </div>
                  <p className="doctor-text">"Muy buena atención, muy puntual"</p>
                  <p className="doctor-text"><i className="far fa-clock"></i>8 - 20pm</p>
                  <p className="doctor-text"><i className="fas fa-map-marker-alt"></i> Libertador 1000, CABA</p>
              </div>
          </div>
      </div>
    );
  }
}

export default SpecialistCard;
