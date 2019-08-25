import React, { Component } from 'react';
import { Route, Redirect } from 'react-router-dom'
import './App.css';

import Header from './components/navigation/header'
import Footer from './components/navigation/footer'
import Home from './pages/home'
import Specialists from './pages/specialists'

const App = props => {
  let everyoneRoutes = [
    { path: "/", component: Home },
  ];
  let doctorRoutes = [
    ...everyoneRoutes
  ];
  return(
    <div>
      <Header />
      <main>
        <Route exact path="/" component={Home} />
        <Route exact path="/specialists" component={Specialists} />
      </main>
      <Footer />
    </div>
  )
}

export default App;
