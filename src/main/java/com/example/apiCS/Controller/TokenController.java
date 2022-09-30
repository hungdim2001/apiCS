package com.example.apiCS.Controller;

import com.example.apiCS.Service.TokenService;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/api/token")
@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("/refreshToken")
    public ResponseEntity refreshToken(HttpServletRequest request)  {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tokenService.refreshToken(request));
    }

}
