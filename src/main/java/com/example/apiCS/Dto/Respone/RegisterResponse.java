package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private String fullName;
    private String role;
}
