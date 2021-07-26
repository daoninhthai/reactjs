package com.nt.rookie.post.entity;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "authority")
    private String authority;







}