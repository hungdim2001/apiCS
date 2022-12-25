package com.example.apiCS.Controller;

import com.example.apiCS.Dto.request.*;
import com.example.apiCS.Service.AuthService;
import com.example.apiCS.helper.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/oauth2/signUp")
    public ResponseEntity<ResponseObj> oauth2SignUp(@Valid @RequestBody Oauth2Request Oauth2Request) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "save user successfully", authService.oauth2SignUp(Oauth2Request)));
    }

    @PostMapping("/signIn")
    public ResponseEntity signIn(@Valid @RequestBody LoginRequest user) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "login successfully", authService.signIn(user)));

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/whoami")
    public ResponseEntity whoAmI(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get infor successfully",authService.whoAmI(request)));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/logout")
    public ResponseEntity logout (@RequestBody LogoutRequest logoutRequest){

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "log out successfully",authService.logout(logoutRequest.getToken())));

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/verify")
    public ResponseEntity verify (@RequestBody VerifyRequest verifyRequest,HttpServletRequest httpServletRequest ) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "verify successfully",authService.verify( verifyRequest.getCode(),httpServletRequest)));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/resendCode")
    public ResponseEntity resendCode ( HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "resend code successfully",authService.reSendCode(httpServletRequest)));
    }
    @PostMapping("/findEmail")
    public  ResponseEntity findEmail (@RequestBody FindEmailRequest findEmailRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Please check your email for password reset code",authService.findEmail(findEmailRequest.getEmail())));

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "reset password successfully",authService.resetPassword(resetPasswordRequest.getPassword(),httpServletRequest)));

    }

}
