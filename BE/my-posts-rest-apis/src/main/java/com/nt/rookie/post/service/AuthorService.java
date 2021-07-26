package com.nt.rookie.post.service;

import com.nt.rookie.post.entity.Author;
import com.nt.rookie.post.model.dto.AuthorDTO;
import com.nt.rookie.post.model.dto.PostDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    public Author findAuthorByUsername(String username);
    public AuthorDTO getAuthorById(int id);
}
