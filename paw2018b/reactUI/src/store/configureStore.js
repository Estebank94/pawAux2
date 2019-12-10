import { createStore, applyMiddleware } from 'redux'
import thunkMiddleware from 'redux-thunk'
import { createLogger } from 'redux-logger'
import rootReducer from './rootReducer'
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage' // defaults to localStorage for web

const loggerMiddleware = createLogger()

// export default function configureStore(preloadedState) {
//   return createStore(
//     rootReducer,
//     preloadedState,
//     applyMiddleware(thunkMiddleware, loggerMiddleware)
//   )
// }

const persistConfig = {
  key: 'root',
  storage,
}

const initialState = {}

const persistedReducer = persistReducer(persistConfig, rootReducer)

export const store =
  createStore(persistedReducer, initialState, applyMiddleware(thunkMiddleware, loggerMiddleware))

export const persistor = persistStore(store)