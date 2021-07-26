import React from 'react';
const Welcome = ({ name, age, className }) => {
  // console.log('Welcome render');
  return (
    <div className={ className }>
      <h1>Name: { name }</h1>
      <h2>Age: { age }</h2>
    </div>
  )
}

export default Welcome;
