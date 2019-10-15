/**
 * Created by estebankramer on 14/10/2019.
 */

import axios from "axios";
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
                        'Access-Control-Allow-Origin': '*',
                    }
                }).then(response => response.data)
            } else {
                return axios({
                    url: API_URL + endpoint,
                    method: method,
                    data: body,
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*',
                    }
                }).then(response => response.data)
            }
    }
}