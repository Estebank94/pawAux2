/**
 * Created by estebankramer on 03/12/2019.
 */

import React from 'react'
import { Link } from 'react-router-dom';
import Select from 'react-select';
import queryString from 'query-string';

import fetchApi from '../../utils/api'

class SearchBar extends React.Component {
  state = {
    insurances: [],
    selectedInsurance: null,
    specialties: [],
    selectedSpecialty: null,
    selectedName: '',
    searchQuery: ''
  };

  componentDidMount() {
    fetchApi('/insurances', 'GET')
      .then(response => {
        let insurances = [];
        response.insurances.map(insurance => insurances.push({value: insurance.id, label: insurance.name}))
        this.setState({insurances});
      })
    fetchApi('/specialties', 'GET')
      .then(response => {
        let specialties = [];
        response.specialties.map(specialty => specialties.push({value: specialty.id, label: specialty.speciality}))
        this.setState({specialties});
      })
  }

  async handleChange(e) {
    const { name, value } = e.target;
    await this.setState({ [name]: value });
    this.buildString();
  }

  async handleSelect(e, type) {
    if(type === 'specialty'){
      await this.setState({ selectedSpecialty: e });
    } else {
      await this.setState({ selectedInsurance: e });
    }
    this.buildString();
  }

  buildString() {
    const { selectedName, selectedInsurance, selectedSpecialty } = this.state;
    const name = selectedName === '' ? null : selectedName;
    const insurance = selectedInsurance ? selectedInsurance.label : null;
    const specialty = selectedSpecialty ? selectedSpecialty.label : null;

    let searchQuery = queryString.stringify({ name, insurance, specialty }, { skipNull: true });
    this.setState({ searchQuery })
  }

  render() {
    const { insurances, selectedInsurance, specialties, selectedSpecialty, selectedName } = this.state;
    const { dark } = this.props;
    const customStyles = {
      control: (base, state) => ({
        ...base,
        boxShadow: state.isFocused ? 0 : 0,
        borderColor: dark ? '#FFF' :'#ced4da',
        '&:hover': {
          borderColor: dark ? '#FFF' :'#ced4da',
        }
      })
    };

    return(
      <form>
        <div className="form-row">
          <div className="form-group mb-0 col-md-5 mr-0">
            <input
              type="text"
              aria-label="Buscar por nombre del médico"
              placeholder="Nombre del medico"
              className="form-control"
              name="selectedName"
              value={selectedName}
              style={dark ? { borderColor: '#FFF' } : {}}
              onChange={(e)=> this.handleChange(e)}
            />
          </div>
          <div className="form-group mb-0 col-md-3">
            <Select
              value={selectedSpecialty}
              onChange={(e) => this.handleSelect(e, 'specialty')}
              options={specialties}
              placeholder="Especialidad"
              styles={customStyles}
              isLoading={specialties === []}
            />
          </div>
          <div className="form-group mb-0 col-md-3">
            <Select
              value={selectedInsurance}
              onChange={(e) => this.handleSelect(e, 'insurance')}
              options={insurances}
              placeholder="Prepaga"
              styles={customStyles}
              isLoading={insurances === []}
            />
          </div>
          <div className="col-md-1 pl-1 pr-0 mr-0">
            <Link className={'btn btn-block ' + (dark? 'btn-light' : 'btn-primary custom-btn') } to={'/specialists?' + this.state.searchQuery } style={{ textDecoration: 'none', color: dark? '#000' : '#FFF' }}>
              Buscar
            </Link>
          </div>
        </div>
      </form>
    );
  }
}

export default SearchBar;
