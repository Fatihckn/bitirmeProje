package com.bitirmeproje.controller;

import com.bitirmeproje.dto.LoginDto;
import com.bitirmeproje.dto.UserDto;
import com.bitirmeproje.model.User;
import com.bitirmeproje.security.JwtUtil;
import com.bitirmeproje.service.AuthService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {

        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    //Kullanıcıyı kaydediyoruz
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        authService.register(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    //Kullanıcı oturumunu kontrol edip token döndürüyoruz
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        if("Login Failed".equals(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Login Failed");
        }

        return ResponseEntity.ok(token);
    }
}
