package com.nt.rookie.post.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.rookie.post.entity.Author;
@Service
public class JwtUserDetailsService implements UserDetailsService  {
    @Autowired
    private AuthorService authorService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author =authorService.findAuthorByUsername(username);
        UserBuilder userBuilder =null;
        if(author!=null) {
            userBuilder= User.withUsername(username);
            userBuilder.password(new BCryptPasswordEncoder().encode(author.getPassword()));
            userBuilder.roles(author.getAuthority().getAuthority());
        }else {
            throw new UsernameNotFoundException("Username not found");
        }
        return userBuilder.build();
    }
}