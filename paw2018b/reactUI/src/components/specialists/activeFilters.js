import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import Badge from 'react-bootstrap/Badge';
import PropTypes from 'prop-types';
import i18n from "../../i18n";


class ActiveFilters extends React.Component {
  static propTypes = {
    cancel: PropTypes.func.isRequired,
    data: PropTypes.object,
    cancelable: PropTypes.bool,
  };

  render() {
    const { name, sex, insurance, specialty, insurancePlan, days } = this.props;

    return(
      <div>
        {
          name &&
          <Badge className="badge-waldoc p-2" onClick={() => this.props.handleChange('name', null)}>
            {name} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
          </Badge>
        }
        {
          sex &&
          <Badge className="badge-waldoc p-2" onClick={() => this.props.handleChange('sex', null)}>
            {sex === 'M' ? 'Masculino' : 'Femenino'}
            <FontAwesomeIcon className="ml-1" icon={faTimesCircle} />
          </Badge>
        }
        {
          insurance &&
          <Badge className="badge-waldoc p-2" onClick={() => this.props.handleChange('insurance', null)}>
            {insurance} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
          </Badge>
        }
        {
          specialty &&
          <Badge className="badge-waldoc p-2" onClick={() => this.props.handleChange('specialty', null)}>
            {specialty} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
          </Badge>
        }
        {
          insurancePlan &&
          <Badge className="badge-waldoc p-2" onClick={() => this.props.handleChange('insurancePlan', null)}>
            {insurancePlan} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
          </Badge>
        }
        {
          days &&
          <Badge className="badge-waldoc p-2" onClick={() => this.props.handleChange('days', null)}>
            {i18n.t(`week.${this.props.dayToString(days)}`)} <FontAwesomeIcon className="ml-1" icon={faTimesCircle}/>
          </Badge>
        }

      </div>
    );
  }
}

export default ActiveFilters;
