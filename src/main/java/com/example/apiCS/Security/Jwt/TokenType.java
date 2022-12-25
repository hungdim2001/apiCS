package com.example.apiCS.Security.Jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenType {
    String token;
    Long expiresIn;
}
