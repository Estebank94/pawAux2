import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { render } from 'react-dom';
import { Provider } from 'react-redux'
import './index.css';
import App from './App';
import './i18n';
import configureStore from './store/configureStore.js';

const store = configureStore();


const target = document.querySelector('#root')
render(
  <Provider store={store}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </Provider>
  ,target)
