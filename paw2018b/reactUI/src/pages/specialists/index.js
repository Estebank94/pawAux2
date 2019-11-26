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
        insurances: null,
        specialties: null
    };

    componentDidMount() {
        console.log('params', this.props.location);
        fetchApi('/doctor/list?page=' + 1, 'GET') //TODO poner bien el page del query param
            .then(specialists => {
                    console.log(specialists);
                    this.setState({ specialists: specialists, loading: false });
                })
            .catch(() => this.setState({ error: true, loading: false }));
    }

  render() {
      const { error, loading, specialists } = this.state;
      const PAGE_SIZE = 10;

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
                    <div className="sidebar-nav-fixed pull-right affix">
                        <h3 className="sidebar-title">{i18n.t('prueba')}</h3>
                    </div>
                </div>
            </div>
            <div className="m-t-20 m-b-20">
                <ul className="pagination justify-content-star">
                    <li className="page-item disabled">
                        <a className="page-link" href="#" tabindex="-1">Previous</a>
                    </li>
                    <li className="page-item"><a className="page-link" href="#">1</a></li>
                    <li className="page-item"><a className="page-link" href="#">2</a></li>
                    <li className="page-item"><a className="page-link" href="#">3</a></li>
                    <li className="page-item">
                        <a className="page-link" href="#">Next</a>
                    </li>
                </ul>
            </div>
        </div>
      </div>
    )
  }
}

export default Specialists;
