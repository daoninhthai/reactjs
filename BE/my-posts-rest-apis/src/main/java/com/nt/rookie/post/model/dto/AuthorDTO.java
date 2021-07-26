package com.nt.rookie.post.model.dto;

import com.nt.rookie.post.entity.Authority;
import com.nt.rookie.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Integer id;


    private String username;



    private String firstName;


    private String lastName;



    private String email;



    private Date birthDate;


    private Date createdDate;


    private List<Post> posts;


    private Authority authority;


}
