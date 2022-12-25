package com.example.apiCS.Service;

import com.example.apiCS.Dto.Respone.LoginResponse;
import com.example.apiCS.Dto.Respone.RegisterResponse;
import com.example.apiCS.Dto.Respone.whoAmIResponse;
import com.example.apiCS.Dto.request.LoginRequest;
import com.example.apiCS.Dto.request.Oauth2Request;
import com.example.apiCS.Dto.request.RegisterRequest;
import com.example.apiCS.Entity.*;
import com.example.apiCS.Repository.CodeRepository;
import com.example.apiCS.Repository.RoleRepository;
import com.example.apiCS.Repository.TokenRepository;
import com.example.apiCS.Repository.UserRepository;
import com.example.apiCS.Security.Jwt.JwtUtils;
import com.example.apiCS.Security.Jwt.TokenType;
import com.example.apiCS.commons.Utils.BaseUtils;
import com.example.apiCS.exceptions.DuplicateException;
import com.example.apiCS.exceptions.InvalidLoginException;
import com.example.apiCS.exceptions.InvalidRefreshToken;
import com.example.apiCS.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MailService mailService;
    @Autowired
    private CodeRepository codeRepository;

    public LoginResponse oauth2SignUp(Oauth2Request user) {
        Optional<User> userFound = userRepository.findByEmail(user.getEmail());
        System.out.println("provider: " + userFound.get().getProvider() != null);
        if (userFound.isPresent() && userFound.get().getProvider() != null) {
            TokenType accessToken = jwtUtils.generateJwtToken(userFound.get().getId(), true);
            TokenType refreshToken = jwtUtils.generateJwtToken(userFound.get().getId(), false);
            Token token = Token.builder().token(refreshToken.getToken()).build();
            tokenRepository.save(token);
            userRepository.updateOauth2User(user.getFirstName(), user.getLastName(), user.getAvatarUrl(), user.getEmail());
            return LoginResponse
                    .builder()
                    .accessToken(accessToken.getToken())
                    .expiresIn(accessToken.getExpiresIn() - (new Date()).getTime())
                    .refreshToken(refreshToken.getToken())
                    .refreshExpiresIn(refreshToken.getExpiresIn() - (new Date()).getTime())
                    .build();
        } else if (userFound.isPresent() && userFound.get().getProvider() == null) {
            throw new InvalidLoginException(HttpStatus.UNAUTHORIZED, "Email is exist");
        }
        User newUser = User.builder().email(user.getEmail())
                .isActive(true)
                .provider(user.getAuthProvider())
                .avatarUrl(user.getAvatarUrl())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
        newUser.setRole(userRole);
        userRepository.save(newUser);
        User userResponse = userRepository.findAccount(user.getEmail());
        TokenType accessToken = jwtUtils.generateJwtToken(userResponse.getId(), true);
        TokenType refreshToken = jwtUtils.generateJwtToken(userResponse.getId(), false);
        Token token = Token.builder().token(refreshToken.getToken()).build();
        tokenRepository.save(token);
        return LoginResponse
                .builder()
                .accessToken(accessToken.getToken())
                .expiresIn(accessToken.getExpiresIn() - (new Date()).getTime())
                .refreshToken(refreshToken.getToken())
                .refreshExpiresIn(refreshToken.getExpiresIn() - (new Date()).getTime())
                .build();

    }

    public RegisterResponse signUp(RegisterRequest user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "email have already taken");
        } else if (userRepository.existsByPhone(user.getPhone())) {
            throw new DuplicateException(HttpStatus.CONFLICT, "Phone have already taken");
        }

        User newUser = User.builder()
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .phone(user.getPhone())
                .isActive(false)
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
        String code = BaseUtils.getAlphaNumericString(6);
        Code newCode = Code.builder().code(code).
                expiredTime(new Date().getTime() + 300000).idUser(userResponse.getId())
                .build();
        codeRepository.save(newCode);

        try {
            mailService.sendSimpleEmail(user.getEmail(),
                    "Verify Email",
                    code);
        } catch (Exception e) {
            throw new DuplicateException(HttpStatus.CONFLICT, "can't send email");

        }


        return RegisterResponse.builder()
                .email(userResponse.getEmail())
                .id(userResponse.getId())
                .phone(userResponse.getPhone())
                .fullName(userResponse.getLastName() + " " + userResponse.getFirstName())
                .avatarUrl(userResponse.getAvatarUrl())
                .firstName(userResponse.getFirstName())
                .lastName(userResponse.getLastName())
                .isActive(userResponse.isActive())
                .role(userResponse.getRole().getName().toString())
                .build();
    }

    public whoAmIResponse whoAmI(HttpServletRequest request) {
        String jwt = BaseUtils.parseJwt(request);
        Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(jwt, true));
        User userResponse = userRepository.getById(id);
        return whoAmIResponse.builder()
                .id(userResponse.getId())
                .avatarUrl(userResponse.getAvatarUrl())
                .fullName(userResponse.getLastName() + " " + userResponse.getFirstName())
                .role(userResponse.getRole().getName().toString())
                .phone(userResponse.getPhone())
                .firstName(userResponse.getFirstName())
                .lastName(userResponse.getLastName())
                .isActive(userResponse.isActive())
                .email(userResponse.getEmail())
                .build();
    }

    public LoginResponse signIn(@Valid @RequestBody LoginRequest user) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new InvalidLoginException(HttpStatus.UNAUTHORIZED, "Username or password is in correct");
        }

        User userResponse = userRepository.findAccount(user.getAccount());
        TokenType accessToken = jwtUtils.generateJwtToken(userResponse.getId(), true);
//        System.out.println(accessToken.ge);
        TokenType refreshToken = jwtUtils.generateJwtToken(userResponse.getId(), false);
        Token token = Token.builder().token(refreshToken.getToken()).build();
        tokenRepository.save(token);
        return LoginResponse
                .builder()
                .accessToken(accessToken.getToken())
                .expiresIn(accessToken.getExpiresIn() - (new Date()).getTime())
                .refreshToken(refreshToken.getToken())
                .refreshExpiresIn(refreshToken.getExpiresIn() - (new Date()).getTime())
                .build();

    }

    public String logout(String token) {
        if (!tokenRepository.existsByToken(token)) {
            throw new InvalidRefreshToken(HttpStatus.NOT_FOUND, "Not Found token");
        }

        tokenRepository.removeToken(token);
        return null;
    }

    public String verify(String code, HttpServletRequest request) {
        String jwt = BaseUtils.parseJwt(request);
        Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(jwt, true));
        Code codeFound = codeRepository.findByCode(code).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND, "Not Found code")
        );
        Long currentTime = new Date().getTime();
        userRepository.updateStatusUser(id);
        if (currentTime > codeFound.getExpiredTime()) {
            throw new InvalidRefreshToken(HttpStatus.BAD_REQUEST, "code is expired");
        }

        codeRepository.removeCode(id);
        return null;
    }

    public String reSendCode(HttpServletRequest request) {
        String jwt = BaseUtils.parseJwt(request);
        Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(jwt, true));
        codeRepository.removeCode(id);
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND, "Not user with id")
        );

        String code = BaseUtils.getAlphaNumericString(6);
        Code newCode = Code.builder().code(code).
                expiredTime(new Date().getTime() + 300000).idUser(user.getId())
                .build();
        codeRepository.save(newCode);

        try {
            mailService.sendSimpleEmail(user.getEmail(),
                    "Verify Email",
                    code);
        } catch (Exception e) {
            throw new DuplicateException(HttpStatus.CONFLICT, "can't send email");

        }


        return null;
    }

    public String findEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND, "Email không tồn tại")
        );
        TokenType accessToken = jwtUtils.generateJwtToken(user.getId(), true);

        String message = " Xin chào bạn,\n" +
                "\n" +
                "Để khôi phục lại mật khẩu, xin nhấp vào " + "http://localhost:3000/auth/reset-password/" +
                user.getId() + "/" +
                accessToken.getToken() +
                " để tạo mật khẩu mới." +
                "Link này sẽ hết hạn trong 5 phút";
        try {

            mailService.sendSimpleEmail(user.getEmail(),
                    "Reset Password",
                    message, true);
        } catch (Exception e) {
            throw new DuplicateException(HttpStatus.CONFLICT, "can't send email");
        }
        return null;
    }

    public String resetPassword(String password, HttpServletRequest request) {
        String jwt = BaseUtils.parseJwt(request);
        Long id = Long.valueOf(jwtUtils.getIdFromJwtToken(jwt, true));
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND, "Not user with id")
        );
        String newPassword = encoder.encode(password);
        userRepository.updatePassword(newPassword, user.getId());
        return null;
    }


}
