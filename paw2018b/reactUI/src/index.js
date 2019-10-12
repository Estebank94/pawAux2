import React from 'react';
import { BrowserRouter } from 'react-router-dom'
import { render } from 'react-dom'
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';

const target = document.querySelector('#root')
render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
  ,target)
