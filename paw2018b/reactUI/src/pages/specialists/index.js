import React from 'react';
import BounceLoader from 'react-spinners/BounceLoader';
import queryString from 'query-string';

import fetchApi from '../../utils/api'

import SpecialistCard from '../../components/specialist/card';

class Specialists extends React.Component {
    state = {
        loading: true,
        error: false,
        specialists: null,
        insurances: null,
        specialties: null,
        currentPage: 0,
        pages: null
    };

    componentDidMount() {
      const parsed = queryString.parse(this.props.location.search)
      this.getSpecialists(parsed.page || this.state.currentPage);
    }

    getSpecialists(page) {
      this.setState({ loading: true, currentPage: page })
      fetchApi('/doctor/list?page=' + page, 'GET')
        .then(specialists => {
          console.log(specialists);
          this.setState({ specialists: specialists, pages: specialists.totalPageCount, loading: false });
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
            <span class="page-link">
              {i + 1}
              <span class="sr-only">(current)</span>
            </span>
            :
            <a className="page-link" onClick={() => this.getSpecialists(i)}>{i + 1}</a>
        }

      </li>);
    }

    return p;
  }

  render() {
      const { error, loading, specialists } = this.state;

      if(loading) {
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
                        specialists.doctors.map(specialist => <SpecialistCard data={specialist} />)
                    }
                </div>
                <div className="col-md-3">
                    <div className="sidebar-nav-fixed pull-right affix">
                        <h3 className="sidebar-title">Filtros</h3>
                    </div>
                </div>
            </div>
            <div style={{ marginTop: 20 }}>
                <ul className="pagination justify-content-star">
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
