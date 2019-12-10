import { authTypes } from "./types";

const initialState = {
  auth: null,
  user: null,
  loading: false,
  error: false,
}

export default (state = initialState, action) => {
  switch (action.type) {

    case authTypes.DO_SIGN_IN:
      return {
        ...initialState,
        loading: true
      }

    case authTypes.DO_SIGN_OUT:
      return initialState

    case authTypes.SIGN_IN_SUCCESS:
    case authTypes.REGISTER_COMPLETED:
      return {
        ...state,
        auth: action.data.auth,
        user: action.data.user,
        loading: false,
        error: false
      }

    case authTypes.SIGN_IN_ERROR:
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
