import React from 'react';
import BounceLoader from 'react-spinners/BounceLoader';
import queryString from 'query-string';
import _ from 'lodash';
import Badge from 'react-bootstrap/Badge';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimesCircle, faFrownOpen } from '@fortawesome/free-solid-svg-icons';

import { ApiClient } from '../../utils/apiClient';

import SpecialistCard from '../../components/specialist/card';

class Specialists extends React.Component {
  constructor(props) {
    super(props);
    this.API = new ApiClient();

    this.INITIAL_STATE = {
      loading: true,
      error: false,
      loadingPage: false,
      specialists: null,
      insurances: null,
      insurancePlans: null,
      specialties: null,
      filters: {
        name: null,
        insurance: null,
        insurancePlan: null,
        days: null,
        sex: null,
        specialty: null,
        page: 0
      },
      filtering: false,
      pages: null
    };

    this.state = {
      loading: true,
      error: false,
      loadingPage: false,
      specialists: null,
      insurances: null,
      insurancePlans: null,
      specialties: null,
      filters: {
        name: null,
        insurance: null,
        insurancePlan: null,
        days: null,
        sex: null,
        specialty: null,
        page: 0
      },
      filtering: false,
      pages: null
    };
  }


  async componentDidMount() {
    let filters = this.state.filters;
    const p = queryString.parse(this.props.location.search);
    filters.name = this.getFirstValue(p.name);
    filters.insurance = this.getFirstValue(p.insurance);
    filters.specialty = this.getFirstValue(p.specialty);

    this.setState({ filters });
    await this.getSpecialtiesAndInsurances();
    this.getSpecialists();
  }

  componentWillReceiveProps(nextProps) {
    let filters = this.INITIAL_STATE.filters;
    const p = queryString.parse(nextProps.location.search);
    filters.name = this.getFirstValue(p.name);

    this.setState({ filters })
    this.getSpecialists();
  }

  buildQueryParams() {
    let filters = this.state.filters;
    let str = '?'
    str +=  queryString.stringify(filters, { skipNull: true })

    return str;
  }

  getFirstValue(val) {
    if(_.isArray(val)){
      return val[0]
    }
    return val;
  }

  getSpecialists() {
    this.setState({ loading: true })
    this.API.get('/doctor/list' + this.buildQueryParams())
      .then(response => this.setState({ specialists: response.data, filtering: false, pages: response.data.totalPageCount, loading: false }))
      .catch(() => this.setState({ error: true, loading: false }));
  }

  getSpecialtiesAndInsurances() {
    this.API.get('/homeinfo').then(response => {
      let specialties = [];
      let insurances = [];
      response.data.specialties.map(specialty => specialties.push({value: specialty, label: specialty}))
      response.data.insurances.map(insurance => insurances.push({value: insurance.name, label: insurance.name}))
      this.setState({ insurances, specialties, insurancePlans: response.data.insurances });
    })
  }

  loadMore() {
    this.setState({ loadingPage: true })
    this.API.get('/doctor/list' + this.buildQueryParams())
      .then(response => this.setState({ specialists: this.state.specialists.push(response.data), loadingPage: false}))
      .catch(() => this.setState({ error: true, loadingPage: false }));
  }

  renderPagePicker = () => {
    const { pages, filters } = this.state;
    if(filters.page < pages - 1) {
      return(
        <div
          className="btn btn-light btn-block w-shadow mt-3 w-text-color"
          onClick={() => this.loadMore()}
        >
          Cargar más resultados
        </div>
      )
    }
  }

  async handleChange(type, value) {
    let filters = this.state.filters;
    filters[type] = value;
    await this.setState({ filters, filtering: true });
    this.getSpecialists();
  }

  dayToString(day){
    switch(day) {
      case 1:
        return 'Lunes'
        break;
      case 2:
        return 'Martes'
        break;
      case 3:
        return 'Miercoles'
        break;
      case 4:
        return 'Juevex'
        break;
      case 5:
        return 'Viernes'
        break;
      case 6:
        return 'Sabado'
        break;
      case 7:
        return 'Domingo'
        break;
    }
  }

  render() {
    const { error, loading, filtering, specialists, filters, specialties, insurances, insurancePlans } = this.state;
    const { name, sex, insurance, specialty, insurancePlan, days } = filters;

    const DAYS = [
      { name: 'Lunes', value: 1},
      { name: 'Martes', value: 2},
      { name: 'Miercoles', value: 3},
      { name: 'Jueves', value: 4},
      { name: 'Viernes', value: 5},
      { name: 'Sabado', value: 6},
      { name: 'Domingo', value: 7},
    ]

      if((loading || !specialists || !insurances) &&!filtering) {
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
                  {
                    this.renderPagePicker()
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
                        insurancePlan &&
                        <Badge className="badge-waldoc p-2" onClick={() => this.handleChange('insurancePlan', null)}>
                          {insurancePlan} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
                        </Badge>
                      }
                      {
                        days &&
                        <Badge className="badge-waldoc p-2" onClick={() => this.handleChange('days', null)}>
                          {this.dayToString(days)} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
                        </Badge>
                      }
                      {
                        !name &&
                        <div>
                          <h5 className="mb-1 mt-3">Nombre</h5>
                            <div className="form-group">
                              <input name="name" value={name} type="text" className="form-control w-shadow" onChange={(e) => this.handleChange('name', e.target.value)}/>
                            </div>
                            {/*<div className="btn btn-primary custom-btn">Buscar</div>*/}
                        </div>
                      }
                      {
                        !sex &&
                        <div>
                          <h5 className="mb-1 mt-3">Sexo</h5>
                          <p className="mb-0 clickeable" onClick={() => this.handleChange('sex', 'F')}>Femenino</p>
                          <p className="mb-0 clickeable" onClick={() => this.handleChange('sex', 'M')}>Masculino</p>
                        </div>
                      }
                      {
                        !specialty &&
                        <div>
                          <h5 className="mb-1 mt-3">Especialidad</h5>
                          {
                            specialties.map(s =>  <p className="mb-0 clickeable" onClick={() => this.handleChange('specialty', s.label)}>{s.label}</p>)
                          }
                        </div>
                      }
                      {
                        !insurance &&
                        <div>
                          <h5 className="mb-1 mt-3">Obra Social</h5>
                          {
                            insurances.map(i =>  <p className="mb-0 clickeable" onClick={() => this.handleChange('insurance', i.label)}>{i.label}</p>)
                          }
                        </div>
                      }
                      {
                        insurance && !insurancePlan &&
                        <div>
                          <h5 className="mb-1 mt-3">Plan</h5>
                          {
                            insurancePlans.filter(i => i.name === insurance)[0].plans.map(p =>  <p className="mb-0 clickeable" onClick={() => this.handleChange('insurancePlan', p)}>{p}</p>)
                          }
                        </div>
                      }
                      {
                        !days &&
                        <div>
                          <h5 className="mb-1 mt-3">Día de atención</h5>
                          {
                            DAYS.map(d =>  <p className="mb-0 clickeable" onClick={() => this.handleChange('days', d.value)}>{d.name}</p>)
                          }
                        </div>
                      }

                    </div>
                </div>
            </div>
        </div>
      </div>
    )
  }
}

export default Specialists;
