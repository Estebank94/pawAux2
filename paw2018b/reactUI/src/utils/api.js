/**
 * Created by estebankramer on 14/10/2019.
 */

import axios from "axios";
import queryString from 'query-string';
import { API_URL } from "../constants/constants";

//TODO Remove cross origin headers
export default (endpoint, method, body = {}) => {
    console.log('FETCHING FROM: ' + API_URL + endpoint);
    switch (endpoint) {
        default:
            if (method === 'GET') {
                return axios({
                    url: API_URL + endpoint,
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Headers': 'Authorization',
                        'Access-Control-Allow-Origin': '*',
                    }
                }).then(response => response.data)
            } else if (method === 'XPOST') {
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
            } else {
                return axios({
                    url: API_URL + endpoint,
                    method: method,
                    data: queryString.stringify(body),
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Headers': 'x-access-token',
                        'Access-Control-Allow-Origin': '*',
                    }
                }).then(response => {
                    console.log('POST Response', response);
                    return (response.data)
                })
            }
    }
}