import React from 'react';
import BounceLoader from 'react-spinners/BounceLoader';
import PulseLoader from 'react-spinners/PulseLoader';
import queryString from 'query-string';
import _ from 'lodash';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFrownOpen } from '@fortawesome/free-solid-svg-icons';

import { ApiClient } from '../../utils/apiClient';

import SpecialistCard from '../../components/specialist/card';
import ActiveFilters from '../../components/specialists/activeFilters'
import Filters from '../../components/specialists/filters'
import i18n from "../../i18n";

class Specialists extends React.Component {
  constructor(props) {
    super(props);
    this.API = new ApiClient(props);

    this.INITIAL_STATE = {
      loading: true,
      error: false,
      loadingPage: false,
      specialists: null,
      insurances: null,
      insurancePlans: null,
      specialties: null,
      auxName: null,
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

    this.state = {
      loading: true,
      error: false,
      loadingPage: false,
      specialists: null,
      insurances: null,
      insurancePlans: null,
      specialties: null,
      auxName: null,
      filters: {
        name: null,
        insurance: null,
        insurancePlan: null,
        days: null,
        sex: null,
        specialty: null,
        page: 1
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
    filters.insurance = this.getFirstValue(p.insurance);
    filters.specialty = this.getFirstValue(p.specialty);

    this.setState({ filters })
    this.getSpecialists();
  }

  buildQueryParams() {
    let filters = this.state.filters;
    console.log('filters', filters)
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
    let state = this.state;
    state.loadingPage = true;
    state.filters.page += 1;
    this.setState({ state }, () => {
      this.API.get('/doctor/list' + this.buildQueryParams())
        .then(response => {
          const doctors = state.specialists.doctors.concat(response.data.doctors)
          state.specialists.doctors = doctors
          this.setState({ specialists: state.specialists, loadingPage: false })
        })
        .catch(() => this.setState({ error: true, loadingPage: false }));
      });
  }

  renderPagePicker = () => {
    const { pages, filters, loadingPage } = this.state;
    if(loadingPage) {
      return(
        <div className="btn btn-light btn-block w-shadow mt-3 w-text-color">
          <div className="center-horizontal">
            <PulseLoader
              sizeUnit={"px"}
              size={10}
              color={'#257cbf'}
              loading={true}
            />
          </div>
        </div>
      )
    }
    if(filters.page < pages) {
      return(
        <div
          className="btn btn-light btn-block w-shadow mt-3 w-text-color"
          onClick={() => this.loadMore()}
        >
            {i18n.t('specialist.list')}
        </div>
      )
    }
  }

  handleChange = async (type, value) => {
    let filters = this.state.filters;
    filters[type] = value;
    filters.page = 1;
    await this.setState({ filters, filtering: true }, this.getSpecialists());
  }

  handleInputChange(e) {
    this.setState({ auxName: e.target.value });
  }

  async handleNameSearch() {
    const filters = this.state.filters;
    filters.name = this.state.auxName;
    await this.setState({ filters, auxName: null, filtering: true });
    this.getSpecialists();
  }



  dayToString(day){
    switch(day) {
      case 1:
        return 'monday'
        break;
      case 2:
        return 'tuesday'
        break;
      case 3:
        return 'wednesday'
        break;
      case 4:
        return 'thursday'
        break;
      case 5:
        return 'friday'
        break;
      case 6:
        return 'saturday'
        break;
      case 7:
        return 'sunday'
        break;
    }
  }

  renderSpecialists(filtering, specialists) {
    if(!filtering && specialists.doctors.length === 0){
      return(
        <div className="login-card w-shadow mt-4">
          <FontAwesomeIcon icon={faFrownOpen} color="#257cbf" size="4x"/>
          <h3 className="mt-4">{i18n.t('navigation.noResults')}</h3>
          <p>{i18n.t('navigation.changeFilters')}</p>
        </div>
      )
    }

     if(!filtering) {
       return(specialists.doctors.map((specialist, index) => <SpecialistCard key={index} data={specialist} />))
    }

    if(filtering) {
      return(
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
  }

  render() {
    const { error, loading, filtering, specialists, filters, specialties, insurances, insurancePlans } = this.state;
    const { name, sex, insurance, specialty, insurancePlan, days } = filters;

      if((loading || !specialists || !insurances) &&!filtering) {
          return (
              <div className="body-background centered">
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
              <p>{i18n.t('error.error')}</p>
          )
      }

    return (
      <div className="body-background">
        <div className="container">
            <div className="row">
                <div className="col-md-9">
                  { this.renderSpecialists(filtering, specialists)}
                  { this.renderPagePicker() }
                </div>
                <div className="col-md-3">
                    <div className="sidebar-nav-fixed pull-right affix">
                      <h3>{i18n.t('navigation.filters')}</h3>
                      <ActiveFilters
                        name={name}
                        sex={sex}
                        insurance={insurance}
                        specialty={specialty}
                        insurancePlan={insurancePlan}
                        days={days}
                        handleChange={this.handleChange}
                        dayToString={this.dayToString}
                      />
                      <Filters
                        name={name}
                        sex={sex}
                        insurance={insurance}
                        insurances={insurances}
                        specialty={specialty}
                        specialties={specialties}
                        insurancePlan={insurancePlan}
                        insurancePlans={insurancePlans}
                        days={days}
                        handleChange={this.handleChange}
                        dayToString={this.dayToString}
                        handleInputChange={this.handleInputChange}
                        handleNameSearch={this.handleNameSearch}
                      />
                    </div>
                </div>
            </div>
        </div>
      </div>
    )
  }
}

export default Specialists;
