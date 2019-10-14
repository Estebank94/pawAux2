import React from 'react'
import { Link } from 'react-router-dom';
import Fade from 'react-reveal/Fade';
import Select from 'react-select';

import fetchApi from '../../utils/api'

class JumbotronSearch extends React.Component {
    state = {
        insurances: [],
        selectedInsurance: null,
        specialties: [],
        selectedSpecialty: null,
        selectedName: ''
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

    handleChange(e) {
        console.log('handle', e);
        const { name, value } = e.target;
        this.setState({ [name]: value });
    }

    handleSelect(e, type) {
        if(type === 'specialty'){
            this.setState({ selectedSpecialty: e });
        } else {
            this.setState({ selectedInsurance: e });
        }

    }

  render() {
        const { insurances, selectedInsurance, specialties, selectedSpecialty, selectedName } = this.state;

      const customStyles = {
          option: (provided, state) => ({
              ...provided,
              borderBottom: '1px dotted pink',
              color: state.isSelected ? 'red' : 'blue',
              padding: 20,
          }),
          control: () => ({
              // none of react-select's styles are passed to <Control />
              width: 200,
          }),
          singleValue: (provided, state) => {
              const opacity = state.isDisabled ? 0.5 : 1;
              const transition = 'opacity 300ms';

              return { ...provided, opacity, transition };
          }
      }
    return(
      <div className="jumbotron jumbotron-background min-vh-100">
          <div className="container" style={{ marginBottom: 120 }}>
              <Fade top cascade>
                <p className="jumbotron-subtitle fadeIn">¿Te sentis mal? Busca entre los mejores medicos</p>
                <div className="navbar-search-home">
                    <form>
                        <div className="input-group container">
                            <input
                                type="text"
                                aria-label="Buscar por nombre del médico"
                                placeholder="Nombre"
                                className="form-control"
                                name="selectedName"
                                value={selectedName}
                                onChange={(e)=> this.handleChange(e)}
                            />
                            <Select
                                value={selectedSpecialty}
                                onChange={(e) => this.handleSelect(e, 'specialty')}
                                options={specialties}
                                placeholder="Especialidad"
                                className="custom-select"
                                style={customStyles}
                                isLoading={specialties === []}
                            />
                            {/*<select className="custom-select" name="selectedSpecialty" value={selectedSpecialty} onChange={(e) => this.handleChange(e)}>*/}
                                {/*<option value="">Especialidad</option>*/}
                                {/*{*/}
                                    {/*specialties.map((specialty, index) =>*/}
                                        {/*<option value={specialty.id} key={index}>{specialty.speciality}</option>*/}
                                    {/*)*/}
                                {/*}*/}
                            {/*</select>*/}
                            {/*<select className="custom-select" name="selectedInsurance" value={selectedInsurance} onChange={(e) => this.handleChange(e)}>*/}
                                {/*<option value="">Prepaga</option>*/}
                                {/*{*/}
                                    {/*insurances.map((insurance, index) =>*/}
                                        {/*<option value={insurance.id} key={index}>{insurance.name}</option>*/}
                                    {/*)*/}
                                {/*}*/}
                            {/*</select>*/}
                            <Select
                                value={selectedInsurance}
                                onChange={(e) => this.handleSelect(e, 'insurance')}
                                options={insurances}
                                placeholder="Prepaga"
                                className="custom-select"
                                isLoading={insurances === []}
                            />
                            <div className="input-group-append">
                                <Link className="btn btn-primary custom-btn" to="/specialists" style={{ textDecoration: 'none', color: '#FFF' }}>
                                  Buscar
                                </Link>
                            </div>
                        </div>
                    </form>
                </div>
              </Fade>
          </div>
      </div>
    );
  }
}

export default JumbotronSearch;
