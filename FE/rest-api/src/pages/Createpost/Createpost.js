import "./Createpost.css"

import React, { useEffect, useState } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Spinner from 'react-bootstrap/Spinner';
import axios from 'axios';
import { withRouter } from 'react-router-dom';

function Createpost(props) {
  const [post, setPost] = useState({ id: '', title: '', description: '', content: '' });
  const [showLoading, setShowLoading] = useState(false);
  const apiUrl = "http://localhost:8080/api/v1/create";

  const createPost = (e) => {
    const token = localStorage.getItem('jwttoken')
    const headers = { 
      'Authorization': token
      
  };
    setShowLoading(true);
    e.preventDefault();
    const data = { title: post.title, description: post.description, content: post.content };
    axios.post(apiUrl, data ,{headers})
      .then((result) => {
        setShowLoading(false);
        props.history.push('/api/v1/id/' + result.data.id)
      }).catch((error) => setShowLoading(false));
  };

  const onChange = (e) => {
    e.persist();
    setPost({...post, [e.target.name]: e.target.value});
  }

  return (
    <div className="container form-create">
      {showLoading && 
        <Spinner animation="border" role="status">
          <span className="sr-only">Loading...</span>
        </Spinner> 
      } 
    
        <Form onSubmit={createPost} >
          <Form.Group>
            <Form.Label>Title</Form.Label>
            <Form.Control type="text" name="title" id="title" placeholder="Enter post name" value={post.title} onChange={onChange} />
          </Form.Group>
          <Form.Group>
            <Form.Label>Description</Form.Label>
            <Form.Control type="textarea" name="description" id="description" rows="3" placeholder="Enter post description" value={post.description} onChange={onChange} />
          </Form.Group>
          <Form.Group>
            <Form.Label>Content</Form.Label>
            <Form.Control as="textarea" name="content" id="content" rows="3" placeholder="" value={post.content} onChange={onChange} />
          </Form.Group>
          <Button variant="primary" type="submit">
            Save
          </Button>
        </Form>
    
    </div>
  );
}

export default withRouter(Createpost);