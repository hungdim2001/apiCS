package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
//    private Long id;
    private String accessToken;
    private String refreshToken;
//    private String fullName;
//    private String email;
//    private String address;
//    private String phone;
//    private  String userName;
//    private String role;
//    private String  avatarUrl;
    private Long refreshExpiresIn;
    private Long expiresIn;
}
