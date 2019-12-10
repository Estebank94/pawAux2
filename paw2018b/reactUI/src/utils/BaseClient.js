import axios from 'axios';

class BaseClient {
  constructor(props) {
    console.log('PROPS', props);

    let token;
    let dispatch;
    if (props && props.user) {
      token = props.user.auth;
      // dispatch = props.dispatch;
    }

    const headers = {
      'X-AUTH-TOKEN': token,
      'Content-Type': 'application/json'
    };

    // Create axios instance
    this.instance = axios.create({
      baseURL: 'http://localhost:8080/api/v1',
      timeout: 60000,
      headers,
    });

    // Add a response interceptor for 401
    this.instance.interceptors.response.use(
      (response) => {
        return response;
      }, (error) => {
        let errorResponse = error.response;
        if (errorResponse.status === 401) {
          // dispatch({type: LOGOUT});
          props.history.push('login');
        }
        if (errorResponse.status === 403) {
          // dispatch({type: LOGOUT});
          props.history.push('login');
        }
        if (errorResponse.status === 500) {
          // dispatch({type: LOGOUT});
          props.history.push('/error/500');
        }
        return Promise.reject(error);
      });
  }
}

export default BaseClient;
