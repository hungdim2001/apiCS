package com.example.apiCS.Controller;

import com.amazonaws.services.xray.model.Http;
import com.example.apiCS.Dto.Respone.TokenResponse;
import com.example.apiCS.Security.Jwt.JwtUtils;
import com.example.apiCS.commons.Utils.BaseUtils;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequestMapping("/api/token")
@RestController
public class TokenController {
    @Autowired
    private JwtUtils jwtUtils;
    @GetMapping("/refreshToken")
    public ResponseEntity refreshToken(HttpServletRequest request)  {
        String jwt  = BaseUtils.parseJwt(request);
        if (jwtUtils.validateJwtToken(jwt, false)) {
            Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(jwt, false));
            String accessToken = jwtUtils.generateJwtToken(id,true);
            String refreshToken = jwtUtils.generateJwtToken(id,false);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ResponseObj(HttpStatus.OK.value(), true, "refresh token successfully",
                             TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObj(HttpStatus.BAD_REQUEST.value(),false, "refresh token expried", null));
    }

}
