package com.thevarungupta.spring.boot.blog.api.service;

import com.thevarungupta.spring.boot.blog.api.payload.PostDto;
import com.thevarungupta.spring.boot.blog.api.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePostById(Long id);
}
