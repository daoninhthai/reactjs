package com.nt.rookie.post.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name ="password")
    private String password;

    @Column(name ="first_name")
    private String firstName;

    @Column(name ="last_name")
    private String lastName;


    @Column(name ="email", unique = true, nullable = false)
    private String email;


    @Column(name ="birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Column(name ="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Post> posts;


    @OneToOne(mappedBy = "author")
    private Authority authority;

    
    
    public Author(String username2, String password, Authority authorities) {
		this.username=username2;
		this.password=password;
		this.authority=authorities;
	}

}