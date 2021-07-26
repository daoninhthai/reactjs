package com.nt.rookie.post.controller;


import com.nt.rookie.post.entity.Post;
import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.model.request.CreatePostRequest;
import com.nt.rookie.post.model.request.UpdatePostRequest;
import com.nt.rookie.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(value = "Search post", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=404,message = "No post found"),
            @ApiResponse(code=500,message = "")
    })
    @GetMapping("/search")
    public ResponseEntity<?> searchPost(@RequestParam(name="keyword",required = false,defaultValue = "") String keyword){
        List<Post> posts = postService.searchPost(keyword);
        return ResponseEntity.ok(posts);
    }

    @ApiOperation(value = "Get All post", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=404,message = "abc"),
            @ApiResponse(code=500,message = "abc")
    })
    @GetMapping("/getall")
    public ResponseEntity<?> getAllPosts(){
        log.info("Function name: getAllPosts");
        log.debug("Execute get all post list");
        List<PostDTO> posts = postService.getAllPost();
        log.trace("Return Post");
        return ResponseEntity.ok(posts);
    }


    @ApiOperation(value = "Get post By ID", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=404,message = "abc"),
            @ApiResponse(code=500,message = "abc")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id){
        PostDTO result = postService.getPostById(id);
        return ResponseEntity.ok(result);
    }


    @ApiOperation(value = "Create post", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=400,message = "Post already exists in the system"),
            @ApiResponse(code=500,message = "abc")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostRequest request) {
        PostDTO result = postService.createPost(request);
        return ResponseEntity.ok(result);
    }




    @ApiOperation(value = "Update post", response = PostDTO.class)
    @ApiResponses({
            @ApiResponse(code=404,message = "No post found"),
            @ApiResponse(code=500,message = "")
    })
    @PutMapping("/author/update/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody UpdatePostRequest request, @PathVariable int id) {
        PostDTO result = postService.updatePost(request, id);
        return ResponseEntity.ok(result);
    }


    @ApiOperation(value = "Delete post by id", response = String.class)
    @ApiResponses({
            @ApiResponse(code=404,message = "No post found"),
            @ApiResponse(code=500,message = "")
    })
    @DeleteMapping("/author/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Deleted post");
    }



}