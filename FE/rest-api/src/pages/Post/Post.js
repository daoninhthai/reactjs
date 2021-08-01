import "./Post.css"

import { Link, useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";

import axios from "axios";

const Post = () => {
  const [post, setPost] = useState({
    Id: "",
    Title: "",
    Description: "",
    content: "",
   
  });
  const { id } = useParams();
  useEffect(() => {
    loadPost();
  }, []);
  const loadPost = async () => {
    const res = await axios.get(`http://localhost:8080/api/v1/posts/id/${id}`);
    setPost(res.data);
  };
  return (
    <div className="container py-4 post">
      
      <h2>Id: {id}</h2>
      <hr />
      <ul className="body-post">
        <li className="post-title"> {post.title}</li>
        <li className="post-description"> {post.description}</li>
        <li className="post-content"> {post.content}</li>
      </ul>
      <Link className="btn btn-primary" to="/">
        back to Home
      </Link>
    </div>
  );
};

export default Post;