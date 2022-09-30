package com.thevarungupta.spring.boot.blog.api.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
