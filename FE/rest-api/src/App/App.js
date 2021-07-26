import './App.css';

import { BrowserRouter, Route, Switch } from 'react-router-dom';
import React, { useState } from 'react';

import Aboutme from '../pages/Aboutme/Aboutme';
import Contact from '../pages/Contact/Contact';
import Createpost from '../pages/Createpost/Createpost';
import Editpost from '../pages/Editpost/Editpost';
import Footer from "./Footer/Footer"
import Home from '../pages/Home/Home';
import Login from '../pages/Login/Login';
import Navbar from './NavBar/Navbar';
import Post from '../pages/Post/Post';
import Posts from '../pages/Posts/Posts';
import Profile from '../pages/Profile/Profile';

const initialCurrentUser = {
  userId: null,
  token: null
}

const App = () => {
  const [currentUser, setCurrentUser] = useState(initialCurrentUser);
  const loginSuccess = ({ userId, token }) => setCurrentUser({userId, token});
  const logout = () => setCurrentUser(initialCurrentUser);
  const isUserLoggedIn = Boolean(currentUser.userId);
 
  return (
    <BrowserRouter>
      <div className="app-container">
        <Navbar
          logout = { logout }
          isUserLoggedIn = { isUserLoggedIn }
        />
        <Switch>
          <Route
            path="/"
            exact
          >
            <Home/>
          </Route>
          <Route
            path="/posts"
            exact
          >
            <Posts/>
          </Route>
          <Route
            path="/posts/:id"
            exact
          >
            <Post/>
          </Route>
          <Route
            path="/profile"
            exact
            render={ () => {
              if(!isUserLoggedIn) return (
                <Login
                  title="You need to login to continue"
                  loginSuccess={ loginSuccess }
                />
              );
              return (
                <Profile
                  currentUser = { currentUser }
                />
              )
            }}
          />
          <Route
            path="/editpost/:id"
            exact
          >
            <Editpost/>
          </Route>
          <Route
            path="/createpost"
            exact
          >
            <Createpost/>
          </Route>
          <Route
            path="/Aboutme"
            exact
          >
            <Aboutme/>
          </Route>
          <Route
            path="/contact"
            exact
          >
            <Contact/>
          </Route>

          <Route
            path="/login"
            exact
          >
            <Login
              loginSuccess = { loginSuccess }
            />
          </Route>
        </Switch>
        <Footer />
      </div>
    </BrowserRouter>
  )
}

export default App;
