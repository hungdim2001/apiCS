package com.example.apiCS.Service;

import com.example.apiCS.Dto.Respone.LoginResponse;
import com.example.apiCS.Dto.Respone.RegisterResponse;
import com.example.apiCS.Dto.request.LoginRequest;
import com.example.apiCS.Dto.request.RegisterRequest;
import com.example.apiCS.Entity.ERole;
import com.example.apiCS.Entity.Role;
import com.example.apiCS.Entity.User;
import com.example.apiCS.Repository.RoleRepository;
import com.example.apiCS.Repository.UserRepository;
import com.example.apiCS.Security.Jwt.JwtUtils;
import com.example.apiCS.exceptions.DuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Objects;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public RegisterResponse signUp(RegisterRequest user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "email have already taken");
        } else if (userRepository.existsByUserName(user.getUsername())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "username have already taken");
        } else if (userRepository.existsByPhone(user.getPhone())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "Phone have already taken");
        }
        User newUser = User.builder()
                .email(user.getEmail())
                .userName(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .address(user.getAddress())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        if ("admin".equals(user.getRole())) {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role admin is not found."));
            newUser.setRole(adminRole);
        } else {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
            newUser.setRole(userRole);
        }
        User userResponse = userRepository.save(newUser);
        return RegisterResponse.builder()
                .email(userResponse.getEmail())
                .username(userResponse.getUserName())
                .id(userResponse.getId())
                .address(userResponse.getAddress())
                .phone(userResponse.getPhone())
                .fullName(userResponse.getLastName() + " " + userResponse.getFirstName())
                .role(userResponse.getRole().getName().toString())
                .build();
    }

    public LoginResponse signIn(@Valid @RequestBody LoginRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userResponse = userRepository.findAccount(user.getAccount());
        String accessToken = jwtUtils.generateJwtToken(userResponse.getId(), true);
        String refreshToken = jwtUtils.generateJwtToken(userResponse.getId(), false);
        return LoginResponse
                .builder()
                .id(userResponse.getId())
                .avatarUrl(userResponse.getAvatarUrl())
                .fullName(userResponse.getLastName() + " " + userResponse.getFirstName())
                .role(userResponse.getRole().getName().toString())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }
}
