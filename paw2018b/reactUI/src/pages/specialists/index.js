import React from 'react';
import BounceLoader from 'react-spinners/BounceLoader';
import queryString from 'query-string';
import _ from 'lodash';
import Badge from 'react-bootstrap/Badge';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimesCircle, faFrownOpen } from '@fortawesome/free-solid-svg-icons';

import fetchApi from '../../utils/api'

import SpecialistCard from '../../components/specialist/card';

class Specialists extends React.Component {
  INITIAL_STATE = {
    loading: true,
    error: false,
    specialists: null,
    insurances: null,
    specialties: null,
    currentPage: 0,
    filters: {
      name: null,
      insurance: null,
      insurancePlan: null,
      days: null,
      sex: null,
    },
    filtering: false,
    pages: null
  };

  state = {
    loading: true,
    error: false,
    specialists: null,
    insurances: null,
    specialties: null,
    currentPage: 0,
    filters: {
      name: null,
      insurance: null,
      insurancePlan: null,
      days: null,
      sex: null,
      specialty: null,
    },
    filtering: false,
    pages: null
  };

  componentDidMount() {
    let filters = this.state.filters;
    const p = queryString.parse(this.props.location.search);
    filters.name = this.getFirstValue(p.name);
    filters.insurance = this.getFirstValue(p.insurance);
    filters.specialty = this.getFirstValue(p.specialty);

    this.setState({ filters })
    this.getSpecialists();
  }

  componentWillReceiveProps(nextProps) {
    let filters = this.INITIAL_STATE;
    const p = queryString.parse(nextProps.location.search);
    filters.name = this.getFirstValue(p.name);

    this.setState({ filters })
    this.getSpecialists();
  }

  buildQueryParams() {
    let filters = this.state.filters;
    let str = '?'
    str +=  queryString.stringify(filters, { skipNull: true })
    console.log('BUILD QUERY', str);

    return str;
  }

  getFirstValue(val) {
    if(_.isArray(val)){
      return val[0]
    }
    return val;
  }

  getSpecialists() {
    const page = this.state.currentPage
    this.setState({ loading: true, currentPage: page })
    fetchApi('/doctor/list' +  this.buildQueryParams(),'GET')
      .then(specialists => {
        // console.log(specialists);
        this.setState({ specialists: specialists, filtering: false, pages: specialists.totalPageCount, loading: false });
      })
      .catch(() => this.setState({ error: true, loading: false }));
  }

  renderPagePicker = () => {
    const { pages, currentPage } = this.state;
    let p = [];

    for (let i = 0; i < pages; i++) {
      p.push(<li className={ 'page-item' + currentPage === i ? ' active' : '' }>
        {
          currentPage === i ?
            <span className="page-link">
              {i + 1}
              <span className="sr-only">(current)</span>
            </span>
            :
            <a className="page-link" onClick={() => this.getSpecialists(i)}>{i + 1}</a>
        }

      </li>);
    }

    return p;
  }

  async handleChange(type, value) {
    let filters = this.state.filters;
    filters[type] = value;
    await this.setState({ filters, filtering: true });
    this.getSpecialists();
  }

  render() {
    const { error, loading, filtering, specialists, filters } = this.state;
    const { name, sex, insurance, specialty } = filters;

      if(loading && !filtering) {
          return (
              <div className="centered">
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
                    !filtering &&
                    specialists.doctors.map((specialist, index) => <SpecialistCard key={index} data={specialist} />)
                  }
                  {
                    !filtering && specialists.doctors.length === 0 &&
                    <div className="login-card w-shadow mt-4">
                      <FontAwesomeIcon icon={faFrownOpen} color="#257cbf" size="4x"/>
                      <h3 className="mt-4">No hay resultados</h3>
                      <p>Intenta cambiar los filtros de busqueda</p>
                    </div>
                  }
                  {
                    filtering &&
                    <div className="centered">
                      <BounceLoader
                        sizeUnit={"px"}
                        size={75}
                        color={'rgb(37, 124, 191)'}
                        loading={true}
                      />
                    </div>
                  }
                </div>
                <div className="col-md-3">
                    <div className="sidebar-nav-fixed pull-right affix">
                      <h3>Filtros</h3>
                      {
                        name &&
                        <Badge className="badge-waldoc p-2" onClick={() => this.handleChange('name', null)}>
                          {name} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
                        </Badge>
                      }
                      {
                        sex &&
                        <Badge className="badge-waldoc p-2" onClick={() => this.handleChange('sex', null)}>
                          {sex === 'M' ? 'Masculino' : 'Femenino'}
                          <FontAwesomeIcon className="ml-1" icon={faTimesCircle} />
                        </Badge>
                      }
                      {
                        insurance &&
                        <Badge className="badge-waldoc p-2" onClick={() => this.handleChange('insurance', null)}>
                          {insurance} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
                        </Badge>
                      }
                      {
                        specialty &&
                        <Badge className="badge-waldoc p-2" onClick={() => this.handleChange('specialty', null)}>
                          {specialty} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
                        </Badge>
                      }
                      {
                        !sex &&
                        <div>
                          <h5 className="mb-1 mt-3">Sexo</h5>
                          <p className="mb-0 clickeable" onClick={() => this.handleChange('sex', 'F')}>Femenino</p>
                          <p className="mb-0 clickeable" onClick={() => this.handleChange('sex', 'M')}>Masculino</p>
                        </div>

                      }

                    </div>
                </div>
            </div>
            <div className="mt-3">
                <ul className="pagination justify-content-start mb-0">
                    {
                      this.renderPagePicker()
                    }
                </ul>
            </div>
        </div>
      </div>
    )
  }
}

export default Specialists;
