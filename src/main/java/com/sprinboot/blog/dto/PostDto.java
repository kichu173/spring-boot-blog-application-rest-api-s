package com.sprinboot.blog.dto;

import lombok.Data;

@Data
public class PostDto { // DTO is used to transfer data between client and server
    private Long id;
    private String title;
    private String description;
    private String content;
}
