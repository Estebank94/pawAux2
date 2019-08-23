import React, { Component } from 'react';
import { Route, Redirect } from 'react-router-dom'
import './App.css';

import Navigation from './components/navigation'
import Home from './pages/home'

const Header = () => (
  <header>
    <div>
      <a href="/">
        <span className="logo-provi">Waldoc</span>
      </a>
    </div>
  </header>
)

const App = props => {
  let everyoneRoutes = [
    { path: "/", component: Home },
  ];
  let doctorRoutes = [
    ...everyoneRoutes
  ];
  return(
    <div>
      <Navigation />
      <main>
        <Route exact path="/" component={Home} />
      </main>
    </div>
  )
}

export default App;
