import React from 'react'

import JumbotronSearch from '../../components/home/jumbotronSearch';
import Steps from '../../components/home/steps';

class Home extends React.Component {
  render() {
    return (
      <div className="body-background">
        <JumbotronSearch />
        <Steps />
      </div>
    )
  }
}

export default Home;
