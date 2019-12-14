import React from 'react';
import PropTypes from 'prop-types';
import i18n from "../../i18n";


class Filters extends React.Component {
  static propTypes = {
    name: PropTypes.string,
    sex: PropTypes.string,
    insurance: PropTypes.string,
    insurances: PropTypes.array,
    specialty: PropTypes.string,
    specialties: PropTypes.array,
    insurancePlan: PropTypes.string,
    insurancePlans: PropTypes.array,
    days: PropTypes.number,
    handleChange: PropTypes.func,
    dayToString: PropTypes.func,
    handleInputChange: PropTypes.func,
    handleNameSearch: PropTypes.func
  };

  render() {
    const { name, sex, insurance, insurances, specialties, insurancePlans, insurancePlan, days, specialty,
      handleInputChange, handleNameSearch, handleChange } = this.props;

    const DAYS = [
      { name: 'monday', value: 1},
      { name: 'tuesday', value: 2},
      { name: 'wednesday', value: 3},
      { name: 'thursday', value: 4},
      { name: 'friday', value: 5},
      { name: 'saturday', value: 6},
      { name: 'sunday', value: 7},
    ]

    return(
      <div>
        {
          !name &&
          <div>
            <h5 className="mb-1 mt-3">{i18n.t('register.name')}</h5>
            <div className="input-group mb-3">
              <input name="name" value={name} type="text" className="form-control w-shadow" placeholder={i18n.t('specialist.placeHolderName')} onChange={(e) => handleInputChange(e)}/>
              <div className="input-group-append">
                <span className="input-group-text w-shadow clickeable" onClick={() => handleNameSearch()}>{i18n.t('home.searchButton')}</span>
              </div>
            </div>
          </div>
        }
        {
          !sex &&
          <div>
            <h5 className="mb-1 mt-3">{i18n.t('register.gender')}</h5>
            <p className="mb-0 clickeable" onClick={() => handleChange('sex', 'F')}>{i18n.t('register.female')}</p>
            <p className="mb-0 clickeable" onClick={() => handleChange('sex', 'M')}>{i18n.t('register.male')}</p>
          </div>
        }
        {
          !specialty &&
          <div>
            <h5 className="mb-1 mt-3">{i18n.t('home.placeHolderSpeciality')}</h5>
            {
              specialties.map((s, i) =>  <p key={i}  className="mb-0 clickeable" onClick={() => handleChange('specialty', s.label)}>{s.label}</p>)
            }
          </div>
        }
        {
          !insurance &&
          <div>
            <h5 className="mb-1 mt-3">{i18n.t('specialist.insurancesPlans')}</h5>
            {
              insurances.map((i, index) =>  <p key={index}  className="mb-0 clickeable" onClick={() => handleChange('insurance', i.label)}>{i.label}</p>)
            }
          </div>
        }
        {
          insurance && !insurancePlan &&
          <div>
            <h5 className="mb-1 mt-3">Plan</h5>
            {
              insurancePlans.filter((i) => i.name === insurance)[0].plans.map((p, index) =>  <p key={index}  className="mb-0 clickeable" onClick={() => handleChange('insurancePlan', p)}>{p}</p>)
            }
          </div>
        }
        {
          !days &&
          <div>
            <h5 className="mb-1 mt-3">{i18n.t('specialist.workingDay')}</h5>
            {
              DAYS.map((d, i) =>  <p key={i} className="mb-0 clickeable" onClick={() => handleChange('days', d.value)}>{i18n.t(`week.${d.name}`)}</p>)
            }
          </div>
        }
      </div>
    );
  }
}

export default Filters;
