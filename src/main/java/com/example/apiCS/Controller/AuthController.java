package com.example.apiCS.Controller;

import com.example.apiCS.Dto.Respone.UserResponse;
import com.example.apiCS.Dto.request.LoginRequest;
import com.example.apiCS.Dto.request.UserRequest;
import com.example.apiCS.Entity.User;
import com.example.apiCS.Repository.UserRepository;
import com.example.apiCS.exception.DuplicateException;
import com.example.apiCS.exception.NotFoundException;
import com.example.apiCS.helper.ResponseObj;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api/auth")
@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@Valid @RequestBody UserRequest user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "email have already taken");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "username have already taken");
        }
//        Cart newCart = new Cart();
        User newUser = User.builder().email(user.getEmail()).username(user.getUsername()).password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12))).build();
//        newUser.setCart(newCart);
        User userResponse = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "save user successfully", UserResponse.builder().email(userResponse.getEmail()).username(userResponse.getUsername()).id(userResponse.getId()).build()));
    }

    @PostMapping("/signIn")
    public ResponseEntity signIn(@Valid @RequestBody LoginRequest user) {
        User userFound = userRepository.findByUsernameOrEmail(user.getAccount()).orElseThrow(() -> {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Not Found Account");
        });
        if (!BCrypt.checkpw(user.getPassword(), userFound.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObj(HttpStatus.BAD_REQUEST.value(), false, "password is not correct ", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "login successfully", UserResponse.builder().id(userFound.getId()).email(userFound.getEmail()).username(userFound.getEmail()).build()));

    }
}
