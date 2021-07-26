package com.nt.rookie.post.service;

import com.nt.rookie.post.entity.Post;
import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.model.request.CreatePostRequest;
import com.nt.rookie.post.model.request.UpdatePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface PostService {
    public List<PostDTO> getAllPost();

    public PostDTO getPostById(int id);
    public PostDTO updatePost(UpdatePostRequest request, int id);
    public void deletePost(int id);
    public PostDTO createPost(CreatePostRequest request);
    public List<Post> searchPost(String keyword);

}
