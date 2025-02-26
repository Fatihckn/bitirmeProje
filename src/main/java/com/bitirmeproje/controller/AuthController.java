package com.bitirmeproje.controller;

import com.bitirmeproje.dto.LoginDto;
import com.bitirmeproje.dto.UserDto;
import com.bitirmeproje.helper.RequireUserAccess;
import com.bitirmeproje.helper.UserAccessValidator;
import com.bitirmeproje.model.User;
import com.bitirmeproje.security.JwtUtil;
import com.bitirmeproje.service.AuthService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserAccessValidator userAccessValidator;

    public AuthController(AuthService authService, UserAccessValidator userAccessValidator) {
        this.authService = authService;
        this.userAccessValidator = userAccessValidator;
    }

    //Kullanıcıyı kaydediyoruz
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        authService.registerUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    //Kullanıcı oturumunu kontrol edip token döndürüyoruz
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }

    // Çıkış yapacak kullanıcının da doğru kullanıcı olup olmadığı denenip dönüş yapılıyor.
    @PostMapping("/logout/{id}")
    @RequireUserAccess
    public ResponseEntity<?> logout(@PathVariable int id) {
        //User currentUser =  userAccessValidator.validateUserAccess(id);
        return ResponseEntity.ok("Başarıyla çıkış yapıldı");
    }
}
