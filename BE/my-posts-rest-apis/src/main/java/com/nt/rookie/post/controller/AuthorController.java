package com.nt.rookie.post.controller;

import com.nt.rookie.post.model.dto.AuthorDTO;
import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.service.AuthorService;
import com.nt.rookie.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @ApiOperation(value = "Get author By ID", response = AuthorDTO.class)
    @ApiResponses({
            @ApiResponse(code=404,message = "abc"),
            @ApiResponse(code=500,message = "abc")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable int id){
        AuthorDTO result = authorService.getAuthorById(id);
        return ResponseEntity.ok(result);
    }
}
