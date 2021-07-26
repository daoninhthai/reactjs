package com.nt.rookie.post.model.dto;

import com.nt.rookie.post.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {


    private Integer id;

    private String title;

    private String description;

    private String content;

    private Date createdDate;



}