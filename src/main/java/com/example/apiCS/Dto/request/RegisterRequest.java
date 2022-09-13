package com.example.apiCS.Dto.request;

import com.example.apiCS.annotation.Password;
import com.example.apiCS.annotation.PhoneNumber;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class RegisterRequest {

    @NotBlank(message = "username must not be blank")
    private String username;
    @Password(containsDigit = true)
    private String password;
    @Email
    @NotBlank(message = "email must not be blank")
    private String email;
    @PhoneNumber
    private String phoneNumber;
    @NotBlank
    private String role;
}
