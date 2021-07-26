package com.nt.rookie.post.entity;

import javax.persistence.*;


import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "posts")
public class Post {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;


    @Column(name ="created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;



}