import React from 'react'

class JumbotronSearch extends React.Component {
  render() {
    return(
      <div className="jumbotron jumbotron-background">
          <div className="container padding-top-big padding-bottom-big">
              <p className="jumbotron-subtitle">¿Te sentis mal? Busca entre los mejores medicos</p>
              <div className="navbar-search-home">
                  <form action="${processForm}" method="POST" modelAttribute="search" accept-charset="ISO-8859-1">
                      <div className="input-group container">
                          <input type="text" aria-label="Buscar por nombre del médico" placeholder="Nombre" className="form-control" path="name"/>
                          <select className="custom-select" id="speciality" path="specialty" cssStyle="cursor: pointer;">
                            <option value="noSpecialty" label="Especialidad" selected="Especialidad"/>
                          </select>
                          <input type="text" aria-label="Buscar por especialidad" placeholder="Especialidad" className="form-control" path="specialty"/>
                          <select className="custom-select" id="insurance" path="insurance" cssStyle="cursor: pointer;">
                              <option value="no" label="Prepaga" selected="Prepaga"/>
                          </select>
                          <div className="input-group-append">
                              <input type="submit" className="btn btn-primary custom-btn" value="Buscar" path="submit"/>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
      </div>
    );
  }
}


export default JumbotronSearch;
