package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private String fullName;
    private String role;
    private String  imageUrl;
}
