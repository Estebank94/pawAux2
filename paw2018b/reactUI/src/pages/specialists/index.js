import React from 'react';
import i18n from "../../i18n";
import BounceLoader from 'react-spinners/BounceLoader';

import fetchApi from '../../utils/api'

import SpecialistCard from '../../components/specialist/card';

class Specialists extends React.Component {
    state = {
        loading: true,
        error: false,
        specialists: null,
    };

    componentDidMount() {
        // const { id } = this.props.match.params;
        fetchApi('/doctor/list?page=' + 1, 'GET') //TODO poner bien el page del query param
    .then(specialists => {
            console.log(specialists);
            this.setState({loading: false, specialists });
        })
            .catch(() => this.setState({ loading: false, error: true }));
    }

  render() {
      const { error, loading, specialists } = this.state;

      if(loading) {
          return (
              <div className="align-items-center justify-content-center">
                  <BounceLoader
                      sizeUnit={"px"}
                      size={75}
                      color={'rgb(37, 124, 191)'}
                      loading={true}
                  />
              </div>
          )
      }

      if(error) {
          return (
              <p>Hubo un error!</p>
          )
      }

    return (
      <div className="body-background">
        <div className="container">
            <div className="row">
                <div className="col-md-9">
                    {
                        specialists.doctors.map(specialist => <SpecialistCard data={specialist} />)
                    }
                </div>
                <div className="col-md-3">
                    <div class="sidebar-nav-fixed pull-right affix">
                        <h3 class="sidebar-title">{i18n.t('prueba')}</h3>
                    </div>
                </div>
            </div>
        </div>
      </div>
    )
  }
}

export default Specialists;
