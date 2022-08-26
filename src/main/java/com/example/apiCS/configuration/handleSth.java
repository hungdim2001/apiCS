package com.example.apiCS.configuration;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class handleSth {
    @InitBinder
    public void initBinder(WebDataBinder binder){

    }

}
