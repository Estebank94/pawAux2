import axios from 'axios';
import { API_URL } from "../constants/constants";

class BaseClient {
  constructor(props) {

    let token;
    if (props && props.user) {
      token = props.user.auth;

    }

    const headers = {
      'X-AUTH-TOKEN': token,
      'Content-Type': 'application/json; charset=utf-8'
    };

    this.instance = axios.create({
      baseURL: API_URL,
      timeout: 60000 * 2,
      headers,
    });

    this.instance.interceptors.response.use(
      (response) => {
        return response;
      }, (error) => {
        let errorResponse = error.response;
        if(!errorResponse) {
          props.history.push('/error/Error');
        }
        if (errorResponse.status === 401) {
          props.history.push('/error/401');
        }
        if (errorResponse.status === 403) {
          props.history.push('/error/403');
        }
        if (errorResponse.status === 500) {
          props.history.push('/error/500');
        }
        return Promise.reject(error);
      });
  }
}

export default BaseClient;
