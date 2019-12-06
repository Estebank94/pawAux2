import fetchApi from "../api";
import { authTypes } from "./types";

export const doAuth = () => {
  return {
    type: authTypes.DO_AUTH
  }
}

export const doSignOut = () => {
  return {
    type: authTypes.DO_SIGNOUT
  }
}

export const onRegisterCompleted = data => {
  return {
    type: authTypes.REGISTER_COMPLETED,
    data
  }
}

export const onRegisterError = error => {
  return {
    type: authTypes.REGISTER_ERROR,
    error
  }
}

export const onSigninSuccess = data => {
  return {
    type: authTypes.SIGNIN_SUCCESS,
    data
  }
}

export const onSigninError = error => {
  return {
    type: authTypes.SIGNIN_ERROR,
    error
  }
}

export const doLogin = (credential) => {
  return async (dispatch) => {
    dispatch(doAuth());
    let onLogin = fetchApi('/patient/login', 'XPOST', credential).then((auth) => {
      if (auth !== undefined && auth !== null) {
        fetchApi('/patient/me', 'GET', { auth })
        dispatch(onSigninSuccess(auth))
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

export const doSignUp = (newUser) => {
  return async (dispatch) => {
    dispatch(doAuth());
    let onRegister = fetchApi("/users/signup", "POST", newUser).then((auth) => {
      dispatch(onRegisterCompleted(auth))
      return { status: 'authenticated', message: auth};
    }).catch(err => {
      dispatch(onRegisterError(err))
      return { status: 'failed', message: err };
    })
    return onRegister;
  }
}


export const doSingout = () => {
  return async (dispatch) => {
    dispatch(doSignOut());
    return { status: 'un-authenticated'};
  }
}