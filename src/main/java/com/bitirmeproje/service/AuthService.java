package com.bitirmeproje.service;

import com.bitirmeproje.dto.LoginDto;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.security.JwtAuthenticationFilter;
import com.bitirmeproje.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Kullanıcı kayıt servisi
    public void register(@RequestBody User user) {
        user.setSifre(passwordEncoder.encode(user.getSifre()));
        userRepository.save(user);
    }

    // Kullanıcı giriş servisi
//    public String login(@RequestBody LoginDto loginDto) {
//
//        Optional<User> beklenenKullanici = userRepository.findByEPosta(loginDto.getePosta());
//        if (beklenenKullanici.isPresent() && passwordEncoder.matches(loginDto.getSifre(), beklenenKullanici.get().getSifre())) {
//            return "Login OK";
//        }
//        return "Login Failed";
//    }
    public String login(LoginDto loginDto) {
        Optional<User> beklenenKullanici = userRepository.findByEPosta(loginDto.getePosta());
        if (beklenenKullanici.isPresent()) {
            User user = beklenenKullanici.get();
            if (passwordEncoder.matches(loginDto.getSifre(), user.getSifre())) {
                // Giriş başarılıysa token üret
                return jwtUtil.generateToken(user.getePosta());
            }
        }
        return "Login Failed";
    }
}
