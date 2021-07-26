package com.nt.rookie.post.service.impl;


import com.nt.rookie.post.entity.Post;
import com.nt.rookie.post.exception.DuplicateRecordException;
import com.nt.rookie.post.exception.InternalServerException;
import com.nt.rookie.post.exception.NotFoundException;
import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.model.mapper.PostMapper;
import com.nt.rookie.post.model.request.CreatePostRequest;
import com.nt.rookie.post.model.request.UpdatePostRequest;
import com.nt.rookie.post.repository.PostRepository;
import com.nt.rookie.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts =postRepository.findAll();
        List<PostDTO> result = new ArrayList<>();
        for (Post post:posts){
            result.add(PostMapper.toPostDTO(post));
        }
        return result;
    }

    @Override
    public PostDTO getPostById(int id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()) {
            throw new NotFoundException("No post found");
        }
        return PostMapper.toPostDTO(post.get());
    }


    @Override
    public PostDTO createPost(CreatePostRequest request) {
        Post post = postRepository.findByTitle(request.getTitle());
        if (post != null) {
            throw new DuplicateRecordException("Title post already exist !");
        }

        post = PostMapper.toPost(request);
        postRepository.save(post);

        return PostMapper.toPostDTO(post);
    }




    @Override
    public void deletePost(int id) {
        Optional<Post> user = postRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("No post found");
        }

        try {
            postRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Can't delete post");
        }
    }
    @Override
    public PostDTO updatePost(UpdatePostRequest request, int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new NotFoundException("No post found");
        }

        Post updatePost = PostMapper.toPost(request, id);
        try {
            postRepository.save(updatePost);
        } catch (Exception ex) {
            throw new InternalServerException("Can't update post");
        }

        return PostMapper.toPostDTO(updatePost);
    }


    @Override
    public List<Post> searchPost(String keyword ) {
        return postRepository.findByKeywordContains(keyword);
    }



}
