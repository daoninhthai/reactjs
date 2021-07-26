import React from 'react';
import './Input.css';
import { ErrorMessage } from 'formik';

const Input = ({ className, field, form: { touched, errors }, ...otherProps }) => (
  <div className={ className }>
    <input
      className={ 'Input--root' }
      { ...field }
      { ...otherProps }
    />
    <ErrorMessage
      className="Input--field-error"
      name={ field.name }
      component="div"
    />
  </div>
);

export default Input;
