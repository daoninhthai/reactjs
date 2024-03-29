import "./Createpost.css"

import React, { useState } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Spinner from 'react-bootstrap/Spinner';
import axios from 'axios';
import { withRouter } from 'react-router-dom';

function Createpost(props) {
  const [post, setPost] = useState({ id: '',avatar:'./images/unnamed.jpg', title: '', description: '', content: '' });
  const [showLoading, setShowLoading] = useState(false);
  

  const createPost = (e) => {
    const token = localStorage.getItem('jwttoken')
    
    const headers = { 
      'Authorization': token
      
  };
    setShowLoading(true);
    e.preventDefault();
    const data = {avatar: post.avatar, title: post.title, description: post.description, content: post.content };
    axios.post("http://localhost:8080/api/v1/posts/author", data ,{headers})
      .then((result) => {
        setShowLoading(false);
        props.history.push('/api/v1/posts/id/' + result.data.id)
      }).catch((error) => {
        setShowLoading(false)
        if (error.status === 401 || 403) {
          alert("You do not have permission to create posts")
       }
      });
  };

  const onChange = (e) => {
    e.persist();
    setPost({...post, [e.target.name]: e.target.value});
  }

  return (
    <div className="container form-create">
      <h2 className="title-posts-page center">Add post</h2>
      {showLoading && 
        <Spinner animation="border" role="status">
          <span className="sr-only">Loading...</span>
        </Spinner> 
      } 
    
        <Form onSubmit={createPost} >
        <Form.Group className="input-form">
            <Form.Label className="input-label" >Avatar</Form.Label>
            <Form.Control type="text" name="avatar" id="avatar" placeholder="Enter image link" value={post.avatar} onChange={onChange} />
          </Form.Group>
          <Form.Group className="input-form">
            <Form.Label className="input-label" >Title</Form.Label>
            <Form.Control type="text" name="title" id="title" placeholder="Enter post name" value={post.title} onChange={onChange} />
          </Form.Group>
          <Form.Group className="input-form">
            <Form.Label className="input-label" >Description</Form.Label>
            <Form.Control type="textarea" name="description" id="description" rows="3" placeholder="Enter post description" value={post.description} onChange={onChange} />
          </Form.Group>
          <Form.Group className="input-form">
            <Form.Label className="input-label" >Content</Form.Label>
            <Form.Control as="textarea" name="content" id="content" rows="3" placeholder="Enter post content" value={post.content} onChange={onChange} />
          </Form.Group>
          <Button className="input-button" variant="primary" type="submit">
            Save
          </Button>
        </Form>
    
    </div>
  
  );
}

export default withRouter(Createpost);