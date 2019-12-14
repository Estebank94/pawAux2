import React, { Component, lazy, Suspense } from 'react';
import { Route, Redirect } from 'react-router-dom'
import { withRouter } from "react-router";
import BounceLoader from 'react-spinners/BounceLoader';
import './App.css';
import Fade from 'react-reveal/Fade';

const Header = lazy(() => import('./components/navigation/header'));
const Footer = lazy(() => import('./components/navigation/footer'));
const Home = lazy(() => import('./pages/home'));
const Specialists = lazy(() => import('./pages/specialists'));
const Specialist = lazy(() => import('./pages/specialist'));
const Login = lazy(() => import('./pages/login'));
const Register = lazy(() => import('./pages/register'));
const Account = lazy(() => import('./pages/account'));
const Confirm = lazy(() => import('./pages/register/confirm'));
const Error = lazy(() => import('./pages/error'));
const Complete = lazy(() => import('./pages/register/completeProfile'));



const LoadingMessage = () => (
  <div className="body-background centered">
    <BounceLoader
      sizeUnit={"px"}
      size={75}
      color={'rgb(37, 124, 191)'}
      loading={true}
    />
  </div>
)

const App = () => {

  const HeaderWithRouter = withRouter(Header);
  const FooterWithRouter = withRouter(Footer);
  return(
      <div>
        <Suspense fallback={<LoadingMessage />}>
          <HeaderWithRouter />
        </Suspense>
        <Suspense fallback={<LoadingMessage />}>
          <Fade>
            <Route exact path="/" component={Home} />
            <Route exact path="/specialists" component={Specialists} />
            <Route exact path="/specialist/:id" component={Specialist} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register/:role" component={Register} />
            <Route exact path="/account" component={Account} />
            <Route exact path="/confirm/:token" component={Confirm} />
            <Route exact path="/error/:error" component={Error} />
            <Route exact path="/complete" component={Complete} />
          </Fade>
        </Suspense>
        <Suspense fallback={<LoadingMessage />}>
          <FooterWithRouter />
        </Suspense>
      </div>
  )
}

export default App;
