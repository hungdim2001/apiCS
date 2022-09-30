package com.example.apiCS.Service;

import com.example.apiCS.Dto.Respone.TokenResponse;
import com.example.apiCS.Security.Jwt.JwtUtils;
import com.example.apiCS.commons.Utils.BaseUtils;
import com.example.apiCS.exceptions.InvalidRefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
@Service
public class TokenService {
    @Autowired
    private JwtUtils jwtUtils;

    public TokenResponse refreshToken(HttpServletRequest request) {
        String jwt  = BaseUtils.parseJwt(request);
        if (!jwtUtils.validateJwtToken(jwt, false)) {
          throw new InvalidRefreshToken(HttpStatus.BAD_REQUEST,"invalid refresh token");
        }
        Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(jwt, false));
        String accessToken = jwtUtils.generateJwtToken(id, true);
        String refreshToken = jwtUtils.generateJwtToken(id, false);
        return  TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
