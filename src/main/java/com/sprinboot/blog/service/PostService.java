package com.sprinboot.blog.service;

import com.sprinboot.blog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(long id);

    PostDto updatePostById(PostDto postDto, long id);
}
