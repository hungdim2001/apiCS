package com.example.apiCS.Controller;

import com.example.apiCS.Dto.Respone.UserResponse;
import com.example.apiCS.Dto.request.LoginRequest;
import com.example.apiCS.Dto.request.RegisterRequest;
import com.example.apiCS.Entity.ERole;
import com.example.apiCS.Entity.Role;
import com.example.apiCS.Entity.User;
import com.example.apiCS.Repository.RoleRepository;
import com.example.apiCS.Repository.UserRepository;
import com.example.apiCS.exceptions.DuplicateException;
import com.example.apiCS.exceptions.NotFoundException;
import com.example.apiCS.helper.ResponseObj;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private RoleRepository roleRepository;
    PasswordEncoder encoder;

    @PostMapping("/signUp")
    public ResponseEntity signUp(@Valid @RequestBody RegisterRequest user) {
        System.out.println("signUp");
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "email have already taken");
        } else if (userRepository.existsByUserName(user.getUsername())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "username have already taken");
        }
        User newUser = User.builder().email(user.getEmail()).userName(user.getUsername()).password(encoder.encode(user.getPassword())).build();
        switch (user.getRole()) {
            case "admin":
                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                newUser.setRole(adminRole);
                break;
            default:
                Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                newUser.setRole(userRole);
                break;
        }
        User userResponse = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "save user successfully", UserResponse.builder().email(userResponse.getEmail()).username(userResponse.getUserName()).id(userResponse.getId()).build()));
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
