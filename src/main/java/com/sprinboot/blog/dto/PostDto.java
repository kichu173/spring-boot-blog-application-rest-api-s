package com.sprinboot.blog.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto { // DTO is used to transfer data between client and server
    private Long id;
    private String title;
    private String description;
    private String content;
    // we are using PostDto to get all posts and get post by id in response, here adding field to get comment info associated to post
    private Set<CommentDto> comments;
}
