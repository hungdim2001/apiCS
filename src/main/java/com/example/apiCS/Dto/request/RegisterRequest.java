package com.example.apiCS.Dto.request;

import com.example.apiCS.annotation.Password;
import com.example.apiCS.annotation.PhoneNumber;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Builder
@Data
public class RegisterRequest {

    @NotBlank(message = "username must not be blank")
    @Size(min = 6, max= 18, message = "Username must be more than 6 characters and less than 18 characters")
    private String username;
    @Password(containsDigit = true)
    private String password;
    @Email
    @NotBlank(message = "email must not be blank")
    private String email;
    @PhoneNumber
    @NotBlank(message = "phone must not be blank")
    @Size(min=10,max = 10, message = "Phone number must be 10 characters")
    private String phone;
    private String firstName;
    private String lastName;
    private String address;
    private String role;
}
