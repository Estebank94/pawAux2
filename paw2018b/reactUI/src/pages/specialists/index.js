import React from 'react'

import SpecialistCard from '../../components/specialist/card';

class Specialists extends React.Component {
  render() {
    return (
      <div className="body-background">
        <div className="main-container">
          <div className="col-md-9">
            <SpecialistCard />
          </div>
        </div>
      </div>
    )
  }
}

export default Specialists;
