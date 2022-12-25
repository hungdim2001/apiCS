package com.example.apiCS.Service;

import com.example.apiCS.Dto.Respone.TokenResponse;
import com.example.apiCS.Repository.TokenRepository;
import com.example.apiCS.Security.Jwt.JwtUtils;
import com.example.apiCS.Security.Jwt.TokenType;
import com.example.apiCS.exceptions.InvalidRefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private TokenRepository tokenRepository;

    public TokenResponse refreshToken(String rfToken) {

        if (!jwtUtils.validateJwtToken(rfToken, false)) {
          throw new InvalidRefreshToken(HttpStatus.BAD_REQUEST,"invalid refresh token");
        }
        if(!tokenRepository.existsByToken(rfToken)){
            throw new InvalidRefreshToken(HttpStatus.BAD_REQUEST,"token not exits");
        }
        System.out.println("refresh");
        Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(rfToken, false));
        TokenType accessToken = jwtUtils.generateJwtToken(id, true);
        TokenType refreshToken = jwtUtils.generateJwtToken(id,false);
        tokenRepository.updateToken(refreshToken.getToken(),rfToken);
        return  TokenResponse.builder()
                .accessToken(accessToken.getToken()).
                expiresIn(accessToken.getExpiresIn() - (new Date()).getTime())
                .refreshToken(refreshToken.getToken()).
                refreshExpiresIn(refreshToken.getExpiresIn()-  (new Date()).getTime())
                .build();
    }
}
