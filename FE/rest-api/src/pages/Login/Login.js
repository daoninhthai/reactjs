import "./Login.css"

import { Button, Form } from "react-bootstrap";
import { useEffect, useState } from "react";

import { Alert } from "antd";
import { Formik } from "formik";
import React from "react";
import axios from "axios";

const Login = ({props,loginSuccess,title}) => {
  const [showLoginSuccess, setShowLoginSuccess] = useState(false);
  const [submitError, setSubmitError] = useState("");
  useEffect(() => {
    document.title = "Login";
  }, []);

  return (
    <div className="mainContent container">
       <div className="title-login">{ title && <h5 >{ title }</h5> }</div>
      <Formik
        initialValues={{ username: "", password: "" }}
        validate={(values) => {
          const errors = {};
          if (!values.username) {
            errors.username = "Required";
          }
          if (!values.password) {
            errors.password = "Required";
          }
          return errors;
        }}
        onSubmit={(values, { setSubmitting }) => {
          axios({
            method: "POST",
            url: "http://localhost:8080/authenticate",
            headers: {},
            data: {
              username: values.username,
              password: values.password,
            },
          }
          )
            .then((response) => {
              setSubmitting(false);
              console.log(response);
              localStorage.clear();
              setShowLoginSuccess(true);
              loginSuccess({  token:"Bearer "+ response.data.jwttoken });
              localStorage.setItem("jwttoken","Bearer "+response.data.jwttoken);
              localStorage.setItem("username",values.username);
            
          }).catch(() => {
            })
            .catch((error) => {
              setSubmitting(false);
              console.log(error);
              setSubmitError(
                "Login fails status code: " + error.response.status
              );
            });
        }}
      >
        {({
          values,
          errors,
          touched,
          handleChange,
          handleBlur,
          handleSubmit,
          isSubmitting,
        
        }) => (
          <div className ="align">
          <div className ="">
          <Form className="form login" onSubmit={handleSubmit}>
             <header className="login__header">
             <h3 className="login__title">Login</h3>
             </header>
             <div className="login__body">
            <Form.Group className="mb-3" controlId="formBasicusername">
            
              <Form.Control
                type="text"
                isInvalid={errors.username && touched.username}
                name="username"
                placeholder="Enter username"
                required
                onChange={handleChange}
                onBlur={handleBlur}
                value={values.username}
              />
              <Form.Control.Feedback type="invalid">
                {errors.username}
              </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
            <i class="fa fa-key"></i>
              <Form.Control
                type="password"
                isInvalid={errors.password && touched.password}
                name="password"
                onChange={handleChange}
                onBlur={handleBlur}
                placeholder="Password"
                required
                value={values.password}
              />
              <Form.Control.Feedback type="invalid">
                {errors.password}
              </Form.Control.Feedback>
            </Form.Group>
            {submitError && (
              <Alert
                closable
                style={{ marginBottom: 15 }}
                message={submitError}
                type="error"
                showIcon
              />
            )}
 </div>
 <footer className="login__footer">
            <Button variant="primary" type="submit" disabled={isSubmitting}>
              Submit
            </Button>

            <p><span className="icon icon--info">?</span><a href="#">Forgot Password</a></p>
           </footer>
           { showLoginSuccess &&
            <div className="form--login-success">
              Login success
            </div> }
          </Form>
          </div>
          </div>
        )}
      </Formik>
    </div>
  );
};
export default Login;