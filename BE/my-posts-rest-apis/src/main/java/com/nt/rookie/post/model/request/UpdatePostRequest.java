package com.nt.rookie.post.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePostRequest {
    @NotNull(message = "Title is required")
    @NotEmpty(message = "Title is required")
    @ApiModelProperty(
            example="How to learn java",
            notes="Title cannot be empty",
            required=true
    )
    @JsonProperty("title")
    private String title;



    @NotNull(message = "Description is required")
    @NotEmpty(message = "Description is required")

    @ApiModelProperty(
            example="How to learn java",
            notes="Description cannot be empty",
            required=true
    )
    @JsonProperty("description")
    private String Description ;




    @NotNull(message = "Content is required")
    @NotEmpty(message = "Content is required")

    @ApiModelProperty(
            example="How to learn java 1 2 3",
            notes="Content cannot be empty",
            required=true
    )
    @JsonProperty("content")
    private String Content ;






}
