package com.sprinboot.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto { // DTO is used to transfer data between client and server
    private Long id;
    @NotEmpty// title should not be null or empty
    @Size(min = 2, message = "Post title should have at least 2 characters")// title should have at least 2 characters
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;
    // we are using PostDto to get all posts and get post by id in response, here adding field to get comment info associated to post
    private Set<CommentDto> comments;
}
