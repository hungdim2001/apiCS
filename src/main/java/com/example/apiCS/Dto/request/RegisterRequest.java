package com.example.apiCS.Dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterRequest {
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String address;
    private String role;
}
