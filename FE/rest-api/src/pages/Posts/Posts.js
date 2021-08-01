import './Posts.css'

import React, { useEffect, useState } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';

const Posts = () => {
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(true);
  const [posts, setPosts] = useState([]);
  const [searchText, setSearchText] = useState('');
  const [sortByTitle, setSortByTitle] = useState(null);
  
  const postsFiltered = posts.filter(post => post.title.toLowerCase().includes(searchText.toLowerCase()));
  
  const getPostsSorted = () => {
    if (sortByTitle === null) return postsFiltered;
    return postsFiltered.sort((post1, post2) => {
      if (sortByTitle === 'ASC') return post1.title.localeCompare(post2.title)
      else return post2.title.localeCompare(post1.title)
    });
  }
  
  const postsSorted = getPostsSorted();
  
  useEffect(() => {
    let didCancel = false;
    axios({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/posts'
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
  /*const handleRemoveRow = postId => {
    const index = posts.findIndex(post => post.id === postId);
    const newPosts = [...posts];
    newPosts.splice(index, 1);
    setPosts(newPosts);
  };*/
  
  return (
    <div className="container">
      <h2 className="title-posts-page">List of all posts</h2>
      <input
        type="text"
        placeholder="Search by title"
        className="search-by-title"
        value={ searchText }
        onChange={ evt => setSearchText(evt.target.value) }
      />
      <table className="table">
        <thead>
        <tr>
          <th className="th" >ID</th>
          <th className="th" onClick={ () => {
            if (sortByTitle === null) setSortByTitle('ASC');
            if (sortByTitle === 'ASC') setSortByTitle('DES');
            if (sortByTitle === 'DES') setSortByTitle(null);
          } }>Title -- Sort { sortByTitle === null ? '(NONE)' : sortByTitle }
          </th>
          <th className="th">Actions</th>
        </tr>
        </thead>
        <tbody>
        {
          postsSorted.map(post => (
            <tr key={ post.id }>
              <td className="td">{ post.id }</td>
              <td className="td">{ post.title }</td>
              <td className="td">
                <Link to={ `posts/${ post.id }` }>
                  View detail
                </Link>
              </td>
            </tr>
          ))
        }
        </tbody>
      </table>
    </div>
  );
};

export default Posts;
