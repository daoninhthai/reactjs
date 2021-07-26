package com.nt.rookie.post.controller;

import java.util.Date;
import java.util.SplittableRandom;

import javax.validation.Valid;
import com.nt.rookie.post.entity.Post;

import com.nt.rookie.post.model.jwt.StatisticResponse;
import com.nt.rookie.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Valid
@RestController
public class RestPostController {
    @PostMapping("/api/v1/author/post")
    public  Post createNewPost(@RequestBody Post post){
        System.out.println("creating post "+post);
        return  post;
    }
    @GetMapping("/api/v1/admin/post/statistic")
    public ResponseEntity<?> getAuthorStatistic(@RequestParam("author") String author){
        StatisticResponse response =new StatisticResponse();
        response.setAuthor(author);
        response.setNumberOfPosts((new SplittableRandom()).nextInt(50,2000));
        return ResponseEntity.ok(response);
    }
}