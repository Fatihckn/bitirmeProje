package com.bitirmeproje.controller;

import com.bitirmeproje.dto.auth.LoginDto;
import com.bitirmeproje.model.User;
import com.bitirmeproje.service.auth.IAuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<String> registerUser(@RequestBody User user) {
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

    //Kullanıcı oturumunu kontrol edip token döndürüyoruz
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        String token = authService.login(loginDto);

//        Cookie cookie = new Cookie("JSESSION", token);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // ✅ HTTPS zorunlu
//        cookie.setDomain("bitirmeproje.xyz");// ✅ Cross-origin için gerekli

        response.addHeader("Set-Cookie",
                "JSESSION=" + token +
                        "; Path=/; HttpOnly; Secure; SameSite=None; Domain=bitirmeproje.xyz");

        return ResponseEntity.ok(token);
    }

    // Çıkış yapacak kullanıcının da doğru kullanıcı olup olmadığı denenip dönüş yapılıyor.
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Set-Cookie",
                "JSESSION=; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=0; Domain=bitirmeproje.xyz");

        return ResponseEntity.ok("Çıkış Yapıldı.");
    }
}

