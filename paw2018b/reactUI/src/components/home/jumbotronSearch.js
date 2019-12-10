import React from 'react'
import SearchBar from './searchBar'
import Fade from 'react-reveal/Fade';
import i18n from '../../i18n';

class JumbotronSearch extends React.Component {


  render() {
    return(
      <div className="jumbotron jumbotron-background min-vh-100">
          <div className="container" style={{ marginBottom: 120 }}>
              <Fade top cascade>
                <p className="jumbotron-subtitle fadeIn">{i18n.t('home.jumbotronTitle')}</p>
                <div className="navbar-search-home">
                    <SearchBar />
                </div>
              </Fade>
          </div>
      </div>
    );
  }
}

export default JumbotronSearch;
