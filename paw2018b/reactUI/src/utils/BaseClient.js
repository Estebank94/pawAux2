import axios from 'axios';

class BaseClient {
  constructor(props) {

    let token;
    if (props && props.user) {
      token = props.user.auth;

    }

    const headers = {
      'X-AUTH-TOKEN': token,
      'Content-Type': 'application/json'
    };

    this.instance = axios.create({
      baseURL: 'http://localhost:8080/api/v1',
      timeout: 60000,
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
