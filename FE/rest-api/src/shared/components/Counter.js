import React from 'react';
import { useEffect } from 'react';

const Counter = ({ value, handleMinus, handleAdd}) => {
  useEffect(()=> {
    document.title = `Current counter value: ${value}`;
  });
  
  
  return (
    <div>
      <button onClick={ handleMinus }>-</button>
      <span className="counter-value">{ value }</span>
      <button onClick = { handleAdd }>+</button>
    </div>
  )
}

export default Counter;
