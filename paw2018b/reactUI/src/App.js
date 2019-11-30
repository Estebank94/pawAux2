import React, { Component } from 'react';
import { Route, Redirect } from 'react-router-dom'
import { withRouter } from "react-router";
import './App.css';

import Header from './components/navigation/header'
import Footer from './components/navigation/footer'
import Home from './pages/home'
import Specialists from './pages/specialists'
import Specialist from './pages/specialist'
import Login from './pages/login'
import Register from './pages/register'


const App = props => {
  let everyoneRoutes = [
    { path: "/", component: Home },
  ];
  let doctorRoutes = [
    ...everyoneRoutes
  ];
  const HeaderWithRouter = withRouter(Header);
  return(
    <div>
      <HeaderWithRouter />
      <main>
          <Route exact path="/" component={Home} />
          <Route exact path="/specialists" component={Specialists} />
          <Route exact path="/specialist/:id" component={Specialist} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
      </main>
      <Footer />
    </div>
  )
}

export default App;
