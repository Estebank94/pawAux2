/**
 * Created by estebankramer on 14/10/2019.
 */

import axios from "axios";
import queryString from 'query-string';
import { API_URL } from "../constants/constants";

export default (endpoint, method, body = {}) => {
  return axios({
    url: API_URL + endpoint,
    method: 'POST',
    data: queryString.stringify(body),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      'Access-Control-Allow-Headers': 'Authorization',
      'Access-Control-Allow-Origin': '*',
    }
  }).then(response => {
    return (response.headers['x-auth-token'])
  }).catch(error => {
    console.log('error:', error);
  });
}