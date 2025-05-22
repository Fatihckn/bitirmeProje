package com.bitirmeproje.controller;

import com.bitirmeproje.dto.auth.LoginDto;
import com.bitirmeproje.dto.user.RegisterDto;
import com.bitirmeproje.service.auth.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    //Kullanıcıyı kaydediyoruz
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto user) {
        authService.registerUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("E-posta doğrulama kodu gönderildi");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        authService.verifyOtpAndRegister(email, otp);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Kayıt Başarılı");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        String token = authService.login(dto);

        return ResponseEntity.ok(token);
    }

    // Çıkış yapacak kullanıcının da doğru kullanıcı olup olmadığı denenip dönüş yapılıyor.
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("Çıkış Yapıldı.");
    }
}

