package com.example.apiCS.Controller;

import com.example.apiCS.Dto.request.TokenRequest;
import com.example.apiCS.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth")
@RestController
@CrossOrigin
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody TokenRequest token)  {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.refreshToken(token.getRfToken()));
    }

}
