import './App.css';

import { BrowserRouter, Route, Switch } from 'react-router-dom';
import React, { useState } from 'react';

import Aboutme from '../pages/Aboutme/Aboutme';
import AdminPage from '../pages/Admin/AdminPage';
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
 
  token: null
}
const username = localStorage.getItem('username');
const App = () => {
  const [currentUser, setCurrentUser] = useState(initialCurrentUser);
  const loginSuccess = ({ token }) => setCurrentUser({token});
  const logout = () => setCurrentUser(initialCurrentUser);
  const isUserLoggedIn = Boolean(currentUser.token);
 
  return (
    <div>
      
     
   
    <BrowserRouter>  
      <Navbar
          logout = { logout }
          isUserLoggedIn = { isUserLoggedIn }
        />
        <Route path="/admin"
            exact
            render={ () => {
              if(username!=="admin") return (
                <Login
                  title="To access the admin page, please log in with an admin account"
                  loginSuccess={ loginSuccess }
                />
              );
              return (
                <AdminPage
                  
                />
              )
            }}
           />
      <div className="app-container">
      
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
            render={ () => {
              if(!isUserLoggedIn) return (
                <Login
                  title="You need to be logged in to be able to edit posts"
                  loginSuccess={ loginSuccess }
                />
              );
              return (
                <Editpost
                  // currentUser = { currentUser }
                />
              )
            }}
           
          />
         
         
          <Route
            path="/createpost"
            exact
            render={ () => {
              if(!isUserLoggedIn) return (
                <Login
                  title="You need to login to be able to post"
                  loginSuccess={ loginSuccess }
                />
              );
              return (
                <Createpost
                  // currentUser = { currentUser }
                />
              )
            }}
          /> 
          
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
    </div>
  )
}

export default App;
