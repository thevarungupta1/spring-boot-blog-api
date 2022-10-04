# Spring Boot Blog RESTfull API

In this project, we will build a complete rest full API for a Blog site by following all best practices

## 1. Project Setup

Install the following dependencies while creating a project
## Table of Contents
1. Web
2. Spring Data
3. H2 / MySQL Driver
4. Lombok
5. DevTools


## 2. Entity Relationship
There are two entities Post and Comment we have to create OneToMany or ManyToOne Relationship

```bash
public class Post {
  ...
  ...
    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();
}

public class Comment {
  ...
  ...

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "post_id",
            nullable = false
    )
    private Post post;
}
```

## 3. Service DTO's implementation
DTO(Data Transfer Object):
One of the enterprise application architecture trends, the Data Transfer Object Design Pattern, calls for using objects that aggregate and encapsulate data for transfer. A Data Transfer Object functions similarly to a data structure. It does not include any business logic, but serialization and deserialization mechanisms should be included.

```bash

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;
}

@Data
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
```

## 4. Service methods implementation
Service Components are the class file that contains @Service annotation. These class files are used to write business logic in a different layer, separated from the @RestController class file

```bash
@Service
public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePostById(Long id);
}

@Service
public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentUpdate);
    void deleteComment(Long postId, Long commentId);
}

@Service
public class PostServiceImpl implements PostService {
 // write business logic
}

@Service
public class CommentServiceImpl implements CommentService {
 // write business logic
}
```

## 5. Model Mapper
Conversion of Entity to DTO and DTO to Entity using the ModelMapper library

ModelMapper library provides an easier way to convert an entity object to DTO and vice versa.

```bash
pom.xml

<!-- https://mvnrepository.com/artifact/org.modelmapper/modelmapper -->
<dependency>
	<groupId>org.modelmapper</groupId>
	<artifactId>modelmapper</artifactId>
	<version>3.1.0</version>
</dependency>



@Service
public class PostServiceImpl implements PostService {
...
...

  // convert Entity to DTO
    private PostDto mapToDTO(Post post) {
        // not using modelMapper for conversion
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    // convert DTP to Entity
    private Post mapToEntity(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
}

@Service
public class CommentServiceImpl implements CommentService {
...
...
 // convert Entity to DTO
    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    // convert DTO to Entity
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
}
```

## 6. Controller

## 7. Paging and Sorting

## 8. Exception

## 9. Validation

## 10. Swagger
Swagger is an open-source set of rules, specifications, and tools for developing and describing RESTful APIs. The Swagger framework allows developers to create interactive, machine and human-readable API documentation.



## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
