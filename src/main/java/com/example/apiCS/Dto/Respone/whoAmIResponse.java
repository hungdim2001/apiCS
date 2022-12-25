package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class whoAmIResponse {
    private Long id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String role;
    private String avatarUrl;
    private Boolean isActive;

}
