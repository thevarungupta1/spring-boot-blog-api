package com.thevarungupta.spring.boot.blog.api.service.impl;

import com.thevarungupta.spring.boot.blog.api.entity.Post;
import com.thevarungupta.spring.boot.blog.api.payload.PostDto;
import com.thevarungupta.spring.boot.blog.api.repository.PostRepository;
import com.thevarungupta.spring.boot.blog.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(pos -> mapToDTO(pos)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("resource not found"));

        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        // get post by id from database
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("resource not found"));
        // get update data
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        // save the changes
        Post updatedPost = postRepository.save(post);

        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        // get post by id from database
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("resource not found"));
        // delete the post
        postRepository.delete(post);
    }

    // convert Entity to DTO
    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    // convert DTP to Entity
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
