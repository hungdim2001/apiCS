package com.example.apiCS.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public class DuplicateException  extends  RuntimeException{
    private HttpStatus status;
    private String message;


}
