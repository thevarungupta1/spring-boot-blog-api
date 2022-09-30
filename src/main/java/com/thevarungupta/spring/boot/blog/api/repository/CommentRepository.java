package com.thevarungupta.spring.boot.blog.api.repository;

import com.thevarungupta.spring.boot.blog.api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
