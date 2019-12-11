import React, { Component } from 'react';
import { Route, Redirect } from 'react-router-dom'
import { withRouter } from "react-router";
import './App.css';
import Fade from 'react-reveal/Fade';

import Header from './components/navigation/header'
import Footer from './components/navigation/footer'
import Home from './pages/home'
import Specialists from './pages/specialists'
import Specialist from './pages/specialist'
import Login from './pages/login'
import Register from './pages/register'
import Account from './pages/account'
import Confirm from './pages/register/confirm'
import Error from './pages/error'


const App = props => {
  let everyoneRoutes = [
    { path: "/", component: Home },
  ];
  let doctorRoutes = [
    ...everyoneRoutes
  ];
  const HeaderWithRouter = withRouter(Header);
  const FooterWithRouter = withRouter(Footer);
  return(
      <div>
        <HeaderWithRouter />
        <Fade>
          <Route exact path="/" component={Home} />
          <Route exact path="/specialists" component={Specialists} />
          <Route exact path="/specialist/:id" component={Specialist} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register/:role" component={Register} />
          <Route exact path="/account" component={Account} />
          <Route exact path="/confirm/:token" component={Confirm} />
          <Route exact path="/error/:error" component={Error} />
        </Fade>
        <FooterWithRouter />
      </div>
  )
}

export default App;
