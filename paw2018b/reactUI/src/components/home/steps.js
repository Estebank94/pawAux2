import React from 'react'
import i18n from "../../i18n";

const Steps = () => {
  return(
    <div className="container">
        <div className="margin-big">
            <p className="jumbotron-subtitle smaller">{i18n.t('home.title')}</p>
            <p className="jumbotron-text">{i18n.t('home.subtitle')}</p>
        </div>

        <div className="d-flex flex-row margin-bottom-medium">
            <img src="https://i.imgur.com/StIDems.jpg" className="image-rectangle" />
            <div>
                <div className="list-home">
                    <h3>{i18n.t('home.searchTitle')}</h3>
                    <p className="doctor-text">{i18n.t('home.searchSubtitle')}</p>
                </div>
            </div>
        </div>

        <div className="d-flex flex-row-reverse margin-bottom-medium">
            <img src="https://i.imgur.com/N7X4FiE.jpg" className="image-rectangle-right" />
            <div>
                <div className="list-home-right">
                    <h3>{i18n.t('home.chooseTitle')}</h3>
                    <p>{i18n.t('home.chooseSubtitle')}</p>
                </div>
            </div>
        </div>


        <div className="d-flex flex-row pb-5">
            <img src="https://i.imgur.com/yjHKj1P.jpg" className="image-rectangle" />
            <div>
                <div className="list-home">
                    <h3>{i18n.t('home.reserveTitle')}</h3>
                    <p>{i18n.t('home.reserveSubtitle')}</p>
                </div>
            </div>
        </div>
    </div>
  );
}

export default Steps
