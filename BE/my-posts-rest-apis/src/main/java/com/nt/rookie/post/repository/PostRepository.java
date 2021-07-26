package com.nt.rookie.post.repository;

import com.nt.rookie.post.entity.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    public Post findByTitle(String title);

    @Query(value = "SELECT * from posts p where p.title LIKE %:keyword% or p.content LIKE %:keyword% or p.description LIKE %:keyword%", nativeQuery = true)
    public List<Post> findByKeywordContains(@Param("keyword") String keyword);

//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO posts (id,title, description , content) VALUES (id,?,?,?) ", nativeQuery = true)
//    public void createPost(String title, String description, String content, int id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE posts SET title = ?1, description = ?2, content = ?3  WHERE id = ?4", nativeQuery = true)
//    public void updatePost(String title, String description, String content, int id);



}