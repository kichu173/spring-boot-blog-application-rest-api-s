package com.sprinboot.blog.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Post Model information")
@Data
public class PostDto { // DTO is used to transfer data between client and server
    @ApiModelProperty(value = "Blog Post id")
    private Long id;

    @ApiModelProperty(value = "Blog Post title")
    @NotEmpty// title should not be null or empty
    @Size(min = 2, message = "Post title should have at least 2 characters")// title should have at least 2 characters
    private String title;

    @ApiModelProperty(value = "Blog Post desc")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @ApiModelProperty(value = "Blog Post content")
    @NotEmpty
    private String content;

    // we are using PostDto to get all posts and get post by id in response, here adding field to get comment info associated to post
    @ApiModelProperty(value = "Blog Post comments")
    private Set<CommentDto> comments;
}
