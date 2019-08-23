import React from 'react'

const Steps = () => {
  return(
    <div class="container">
        <div class="margin-big">
            <p class="jumbotron-subtitle">Titulo</p>
            <p class="jumbotron-text">Subtitulo</p>
        </div>

        <div class="d-flex flex-row margin-bottom-medium">
            <img src="https://i.imgur.com/StIDems.jpg" class="image-rectangle" />
            <div>
                <div class="list-home">
                    <h3>Titulo</h3>
                    <p class="doctor-text">Subtitulo</p>
                </div>
            </div>
        </div>

        <div class="d-flex flex-row-reverse margin-bottom-medium">
            <img src="https://i.imgur.com/N7X4FiE.jpg" class="image-rectangle-right" />
            <div>
                <div class="list-home-right">
                    <h3>Titulo</h3>
                    <p>Subtitulo</p>
                </div>
            </div>
        </div>

        <div class="d-flex flex-row margin-bottom-medium">
            <img src="https://i.imgur.com/yjHKj1P.jpg" class="image-rectangle" />
            <div>
                <div class="list-home">
                    <h3>Titulo</h3>
                    <p>Subtitulo</p>
                </div>
            </div>
        </div>
    </div>
  );
}

export default Steps
