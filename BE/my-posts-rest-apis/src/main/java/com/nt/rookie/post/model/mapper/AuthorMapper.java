package com.nt.rookie.post.model.mapper;

import com.nt.rookie.post.entity.Author;
import com.nt.rookie.post.entity.Post;
import com.nt.rookie.post.model.dto.AuthorDTO;
import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.model.request.CreatePostRequest;
import com.nt.rookie.post.model.request.UpdatePostRequest;

public class AuthorMapper {
    public static AuthorDTO toAuthorDTO(Author author) {
        AuthorDTO tmp = new AuthorDTO();
        tmp.setId(author.getId());
        tmp.setUsername(author.getUsername());
        tmp.setFirstName(author.getFirstName());
        tmp.setLastName(author.getLastName());
        tmp.setEmail(author.getEmail());
        tmp.setCreatedDate(author.getCreatedDate());
        tmp.setBirthDate(author.getBirthDate());
        tmp.setAuthority(author.getAuthority());
        tmp.setPosts(author.getPosts());
        return tmp;
    }

}
