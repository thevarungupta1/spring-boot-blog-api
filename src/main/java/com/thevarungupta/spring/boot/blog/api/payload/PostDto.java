package com.thevarungupta.spring.boot.blog.api.payload;

import lombok.Data;

import javax.persistence.SecondaryTable;
import java.util.Set;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;
}
