package com.example.apiCS.Controller;

import com.example.apiCS.Dto.request.LoginRequest;
import com.example.apiCS.Dto.request.RegisterRequest;
import com.example.apiCS.Service.AuthService;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/signUp")
    public ResponseEntity signUp(@Valid @RequestBody RegisterRequest user) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "save user successfully", authService.signUp(user)));
    }

    @PostMapping("/signIn")
    public ResponseEntity signIn(@Valid @RequestBody LoginRequest user) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "login successfully", authService.signIn(user)));

    }
}
