package com.bitirmeproje.helper.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Şifreyi hashle
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // Girilen şifre ile hash'lenmiş şifreyi karşılaştır
    public boolean matches(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
