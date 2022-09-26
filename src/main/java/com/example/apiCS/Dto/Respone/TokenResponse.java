package com.example.apiCS.Dto.Respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
private String refreshToken;
private String accessToken;
}
