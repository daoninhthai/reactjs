import React, { useEffect, useState } from 'react';

import axios from 'axios';

const Profile = ({ userId, currentUser }) => {
  const [profile, setProfile] = useState({
    createdAt: '',
    name: '',
    id: ''
  });
  const [isLoading, setIsloading] = useState(true);
  const [error, setError] = useState('');
  
  useEffect(()=> {
    let didCancel = false;
    axios({
      method: 'GET',
      url: `http://localhost:8080/api/v1/author/id/${currentUser.userId}`
    }).then(({ data: { createdAt, name, id } })=>{
      if(!didCancel) {
        setIsloading(false);
        setProfile({
          createdAt,
          name,
          id
        })
      }
    }).catch(()=> {
      if(!didCancel) {
        setError(() => setError('Something went wrong'));
        setIsloading(false)
      }
    })
    return () => didCancel = true;
  }, [currentUser.userId]);
  
  if(isLoading) return (<div>Loading</div>);
  if(error) return error;
  return (
    <div>
      <h3>Profile </h3>
      <div>Name: { profile.name }</div>
      <div>UserID: { profile.id }</div>
    </div>
  );
};

Profile.propTypes = {

};

export default Profile;
