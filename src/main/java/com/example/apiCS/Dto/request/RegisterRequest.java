package com.example.apiCS.Dto.request;

import com.example.apiCS.annotation.Password;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
public class RegisterRequest {

    @NotBlank
    private String username;
    @NotBlank
//    @ValidPassword
    @NotBlank
//    @Pattern()
    @Password(containsDigit = true)
    private String password;
    @Email
    @NotBlank
    private String email;
}
