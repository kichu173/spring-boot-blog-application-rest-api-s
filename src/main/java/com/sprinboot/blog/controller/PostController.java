package com.sprinboot.blog.controller;

import com.sprinboot.blog.dto.PostDto;
import com.sprinboot.blog.dto.PostDtoV2;
import com.sprinboot.blog.dto.PostResponse;
import com.sprinboot.blog.service.PostService;
import com.sprinboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // get post by id rest api versioning through URI path(most used approach and basic one)
    @GetMapping("/api/v2/posts/{id}") // http://localhost:8080/api/posts/api/v2/posts/3 -> Postman
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name = "id") long id) {

        PostDto postDto = postService.getPostById(id);
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDto.getContent());
//        postDtoV2.setComments(postDto.getComments());
        // In this version2 rest api get post by id included tags where in above version you won't get tags.
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
    }

    // get post by id versioning it through query parameters.
    @GetMapping(value="/{id}", params ="version=2")// http://localhost:8080/api/posts/3?version=2
    public ResponseEntity<PostDtoV2> getPostByIdVersion2(@PathVariable(name = "id") long id) {
        PostDto postDto = postService.getPostById(id); // same logic as getPostByIdV2 method gets tags in version=2 and not in version 1.
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDto.getContent());
//        postDtoV2.setComments(postDto.getComments());
        // In this version2 rest api get post by id included tags where in above version you won't get tags.
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
    }

    // get post by id versioning through custom headers
    @GetMapping(value="/{id}", headers="X-API-VERSION=2")// // In headers add X-API-VERSION as attribute key and value -> 2
    public ResponseEntity<PostDtoV2> getPostByIdVersion2CustomHeaders(@PathVariable(name = "id") long id) {
        PostDto postDto = postService.getPostById(id); // same logic as getPostByIdV2 method gets tags in version=2 and not in version 1.
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDto.getContent());
//        postDtoV2.setComments(postDto.getComments());
        // In this version2 rest api get post by id included tags where in above version you won't get tags.
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
    }

    // get post by id versioning through Content Negotiation
    @GetMapping(value="/{id}", produces="application/vnd.javalearn.v2+json")// In headers add Accept as attribute key and value as application/vnd.javalearn.v2+json
    public ResponseEntity<PostDtoV2> getPostByIdVersion2ContentNegotiation(@PathVariable(name = "id") long id) {
        PostDto postDto = postService.getPostById(id); // same logic as getPostByIdV2 method gets tags in version=2 and not in version 1.
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDto.getContent());
//        postDtoV2.setComments(postDto.getComments());
        // In this version2 rest api get post by id included tags where in above version you won't get tags.
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
    }

    // update post by id rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
        PostDto postResponse = postService.updatePostById(postDto, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // delete post by rest api
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
    }
}
