package com.example.apiCS.Dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class LoginRequest {
    @NotBlank
    private  String account;
    @NotBlank
    private String password;
}
