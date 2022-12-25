package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private Long id;
    private String email;
    private String phone;
    private String fullName;
    private String firstName;
    private String lastName;
    private String role;
    private String avatarUrl;
    private Boolean isActive;

}
