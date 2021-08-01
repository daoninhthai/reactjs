import React, { useEffect, useState } from 'react';

import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Spinner from 'react-bootstrap/Spinner';
import axios from 'axios';
import { useParams } from "react-router-dom";

function Editpost(props) {
  const [post, setPost] = useState({ id: '',avatar:'', title: '', description: '', content: '' });
  const [showLoading, setShowLoading] = useState(true);
  
  useEffect(() => {
    const token1 = localStorage.getItem('jwttoken')
    console.log(token1);
    setShowLoading(false);
    const fetchData = async () => {
      const result = await axios(`http://localhost:8080/api/v1/posts/id/${id}`);
      setPost(result.data);
      console.log(result.data);
      setShowLoading(false);
    };

    fetchData();
  }, []);
  const { id } = useParams();
  const editPost = (e) => {
    const token = localStorage.getItem('jwttoken')
    const headers = { 
      'Authorization': token
      
  };
    setShowLoading(true);
    e.preventDefault();
    const data = {avatar:post.avatar, title: post.title, description: post.description, content: post.content };
      axios.put(`http://localhost:8080/api/v1/posts/author/${id}`, data ,{headers})

      .then((result) => {
        setShowLoading(false);
       
        window.location.href = "/";
      }).catch((error) => {
        setShowLoading(false)
        if (error.status === 401 || 403) {
          alert("You do not have permission to edit posts")
       }
      });
  };

  const onChange = (e) => {
    e.persist();
    setPost({...post, [e.target.name]: e.target.value});
  }

  return (
    <div  className="container form-create">
      <h2 className="title-posts-page center">Edit post</h2>
      {showLoading && 
        <Spinner animation="border" role="status">
          <span className="sr-only">Loading...</span>
        </Spinner> 
      } 
     
        <Form className="container" onSubmit={editPost}>
        <Form.Group className="input-form">
            <Form.Label  className="input-label">Avatar </Form.Label>
            <Form.Control type="text" name="avatar" id="title" placeholder="Enter post name" value={post.avatar} onChange={onChange} />
          </Form.Group>
          <Form.Group className="input-form">
            <Form.Label  className="input-label">Title </Form.Label>
            <Form.Control type="text" name="title" id="title" placeholder="Enter post name" value={post.title} onChange={onChange} />
          </Form.Group>
          <Form.Group className="input-form">
          <Form.Label  className="input-label">Description</Form.Label>
            <Form.Control type="text" name="description" id="description" placeholder="Enter post description" value={post.description} onChange={onChange} />
          </Form.Group>
          <Form.Group className="input-form">
            <Form.Label  className="input-label">Content</Form.Label>
            <Form.Control as="textarea" name="content" id="content" rows="3" placeholder="Enter post content" value={post.content} onChange={onChange} />
          </Form.Group>
          <Button className="input-button" variant="primary" type="submit">
            Update
          </Button>
        </Form>
      
    </div>
  );
}

export default Editpost;