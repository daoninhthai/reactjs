import "./NewPost.css";

import { Card, Col, Row } from 'react-bootstrap';
import React, { useEffect, useState } from 'react';

import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';
import MockImg from "./unnamed.jpg";
import axios from 'axios';

export default function NewPost(props) {
    const [posts, setPosts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');
   
    const token = localStorage.getItem('jwttoken')

    const headers = { 
      'Authorization': token
      
  };
    useEffect(() => {
        let didCancel = false;
        axios({
          method: 'GET',
          url: 'http://localhost:8080/api/v1/getall'
        })
          .then(response => {
            if (!didCancel) {
              setIsLoading(false);
              setPosts(response.data);
            }
          })
          .catch(() => {
            if (!didCancel) {
              setIsLoading(false);
              setError('Error while fetching data ')
            }
          })
        return () => {
          didCancel = true
        }
      }, []);
      if (isLoading) return <div>LOADING</div>;
      if (error) return <h1 style={ { color: 'red' } }>Error { error }</h1>
      const deletePost = (id) => {
        setIsLoading(true);
        const post = { title: posts.title, description: posts.description, content: posts.content };
        axios.delete(`http://localhost:8080/api/v1/author/delete/${id}`, post ,{headers})
          .then((result) => {
            setIsLoading(false);
            window.location.href = "/";
          }).catch((error) => setIsLoading(false));
      };
    return (
        <div  className="new-post">
           <h2 className="tag-aqua">Top 10 newest post</h2>
            <Row xs={1} md={3} className="g-4 " >
                {posts.map((post) => (
                <Col>
                <Card  key={ post.id } className="card">
                    <Card.Img variant="top" src={MockImg} className="mock-img card-header"/>
                    <Card.Body className="card-body">
                    <Link to={`posts/${ post.id }`}><Card.Title className="tag tag-teal" >{post.title}</Card.Title></Link> 
                    <Card.Text className="description">
                       {post.description}
                    </Card.Text>
                    <div className="btn">
                    <Link  to={`posts/${ post.id }`}><Button variant="info">View</Button>{' '}</Link>
                    <Link to={`editpost/${ post.id} `}><Button variant="success">Edit</Button>{' '}</Link>
                    
                    <Link to=""><Button variant="danger" onClick={() => { deletePost(post.id) }}>Delete</Button> {' '}</Link>
                    </div>
                    </Card.Body>

                </Card>
                </Col>
            ))}
            </Row>
           
      
    
   
        </div>
        
    )
}
