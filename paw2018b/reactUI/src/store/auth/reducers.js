import { authTypes } from "./types";

const initialState = {
  auth: null,
  user: null,
  loading: false,
  error: false,
}

export default (state = initialState, action) => {
  switch (action.type) {

    case authTypes.DO_AUTH:
      return {
        ...initialState,
        loading: true
      }

    case authTypes.DO_SIGNOUT:
      return initialState

    case authTypes.SIGNIN_SUCCESS:
    case authTypes.REGISTER_COMPLETED:
      return {
        ...state,
        auth: action.data.token,
        user: action.data.user,
        loading: false,
        error: false
      }

    case authTypes.SIGNIN_ERROR:
    case authTypes.REGISTER_ERROR:
      console.log('ERROR: '+action.error);
      return {
        ...state,
        loading: false,
        error: true
      }


    default:
      return state;
  }
}
