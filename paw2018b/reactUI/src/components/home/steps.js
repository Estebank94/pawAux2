import React from 'react'

const Steps = () => {
  return(
    <div className="container">
        <div className="margin-big">
            <p className="jumbotron-subtitle">Titulo</p>
            <p className="jumbotron-text">Subtitulo</p>
        </div>

        <div className="d-flex flex-row margin-bottom-medium">
            <img src="https://i.imgur.com/StIDems.jpg" className="image-rectangle" />
            <div>
                <div className="list-home">
                    <h3>Titulo</h3>
                    <p className="doctor-text">Subtitulo</p>
                </div>
            </div>
        </div>

        <div className="d-flex flex-row-reverse margin-bottom-medium">
            <img src="https://i.imgur.com/N7X4FiE.jpg" className="image-rectangle-right" />
            <div>
                <div className="list-home-right">
                    <h3>Titulo</h3>
                    <p>Subtitulo</p>
                </div>
            </div>
        </div>

        <div className="d-flex flex-row pb-5">
            <img src="https://i.imgur.com/yjHKj1P.jpg" className="image-rectangle" />
            <div>
                <div className="list-home">
                    <h3>Titulo</h3>
                    <p>Subtitulo</p>
                </div>
            </div>
        </div>
    </div>
  );
}

export default Steps
