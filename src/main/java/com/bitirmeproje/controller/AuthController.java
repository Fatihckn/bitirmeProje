package com.bitirmeproje.controller;

import com.bitirmeproje.dto.auth.LoginDto;
import com.bitirmeproje.model.User;
import com.bitirmeproje.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {return ResponseEntity.ok("Başarıyla çıkış yapıldı");}
}
