package com.nt.rookie.post.service.impl;

import com.nt.rookie.post.entity.Author;
import com.nt.rookie.post.entity.Post;
import com.nt.rookie.post.exception.NotFoundException;
import com.nt.rookie.post.model.dto.AuthorDTO;
import com.nt.rookie.post.model.dto.PostDTO;
import com.nt.rookie.post.model.mapper.AuthorMapper;
import com.nt.rookie.post.model.mapper.PostMapper;
import com.nt.rookie.post.repository.AuthorRepository;
import com.nt.rookie.post.repository.PostRepository;
import com.nt.rookie.post.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findAuthorByUsername(String username) {

        return(authorRepository.findByUsername(username));
    }
    @Override
    public AuthorDTO getAuthorById(int id) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isEmpty()) {
            throw new NotFoundException("No author found");
        }
        return AuthorMapper.toAuthorDTO(author.get());
    }

}
