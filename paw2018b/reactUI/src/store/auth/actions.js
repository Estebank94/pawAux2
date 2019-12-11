import fetchApi from "../../utils/api";
import { ApiClient } from '../../utils/apiClient'
import { authTypes } from "./types";

export const doSignIn = () => {
  return {
    type: authTypes.DO_SIGN_IN
  }
}

export const doSignOut = () => {
  return {
    type: authTypes.DO_SIGN_OUT
  }
}

export const onSigninSuccess = data => {
  return {
    type: authTypes.SIGN_IN_SUCCESS,
    data
  }
}

export const onSigninError = error => {
  return {
    type: authTypes.SIGN_IN_ERROR,
    error
  }
}

export const doLogin = (credentials, props = {}) => {
  return async (dispatch) => {
    dispatch(doSignIn());
    let onLogin = fetchApi('/patient/login', 'XPOST', credentials).then((auth) => {
      if (auth !== undefined && auth !== null) {
        const user = {
          auth
        }
        const API = new ApiClient({ props, user });
        API.get('/patient/me').then((response => {
          dispatch(onSigninSuccess({auth, user: response.data}))
        }))
        return { status: 'authenticated', message: auth};
      } else {
        dispatch(onSigninError())
        return { status: 'failed', message: 'Contraseña o Usuario Incorrecto!'};
      }
    }).catch(err => {
      dispatch(onSigninError(err))
      return { status: 'failed', message: err};
    })
    return onLogin;
  }
}



export const doSingout = () => {
  return async (dispatch) => {
    dispatch(doSignOut());
    return { status: 'un-authenticated'};
  }
}