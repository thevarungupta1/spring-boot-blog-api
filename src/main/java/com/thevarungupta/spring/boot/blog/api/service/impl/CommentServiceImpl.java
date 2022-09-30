package com.thevarungupta.spring.boot.blog.api.service.impl;

import com.thevarungupta.spring.boot.blog.api.entity.Comment;
import com.thevarungupta.spring.boot.blog.api.entity.Post;
import com.thevarungupta.spring.boot.blog.api.payload.CommentDto;
import com.thevarungupta.spring.boot.blog.api.repository.CommentRepository;
import com.thevarungupta.spring.boot.blog.api.repository.PostRepository;
import com.thevarungupta.spring.boot.blog.api.service.CommentService;
import com.thevarungupta.spring.boot.blog.api.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieve post by id
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new RuntimeException("resource not found"));

        // set post to comment entity
        comment.setPost(post);
        // save comment to database
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        // retrieve comment by postId - create a custom method in repository
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert list of comment to list of commentDto
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("resource not found"));
        // retrieve comment by id
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new RuntimeException("resource not found"));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new RuntimeException("comment does not belong to post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentUpdate) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("resource not found"));
        // retrieve comment by id
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new RuntimeException("resource not found"));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new RuntimeException("comment does not belong to post");
        }

        comment.setName(commentUpdate.getName());
        comment.setEmail(commentUpdate.getEmail());
        comment.setBody(commentUpdate.getBody());

        Comment commentUpdated = commentRepository.save(comment);
        return mapToDTO(commentUpdated);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("resource not found"));
        // retrieve comment by id
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new RuntimeException("resource not found"));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new RuntimeException("comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    // convert Entity to DTO
    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    // convert DTO to Entity
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
