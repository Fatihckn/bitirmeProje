package com.bitirmeproje.service;

import com.bitirmeproje.dto.LoginDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.Role;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.security.JwtAuthenticationFilter;
import com.bitirmeproje.security.JwtUtil;
import org.springframework.http.HttpStatus;
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
    public void registerUser(@RequestBody User user) {
        if(user.getKullaniciRole() == null) {
            user.setKullaniciRole(Role.USER);
        }

        if(userRepository.findByEPosta(user.getePosta()).isPresent() || userRepository.findByKullaniciTakmaAd(user.getKullaniciTakmaAd()).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Bu e-posta veya kullanıcı adı zaten kullanılıyor!");
        }

        user.setSifre(passwordEncoder.encode(user.getSifre()));
        userRepository.save(user);
    }
    public String login(LoginDto loginDto) {
        Optional<User> beklenenKullanici = userRepository.findByEPosta(loginDto.getePosta());

        if (beklenenKullanici.isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,"E-posta veya şifre hatalı ya da kayıtlı değil!") ;
        }

        User user = beklenenKullanici.get();
        if (!passwordEncoder.matches(loginDto.getSifre(), user.getSifre())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,"Hatalı şifre!");
        }

        // Başarılı giriş, token üret ve geri döndür
        return jwtUtil.generateToken(user.getePosta(), user.getKullaniciRole().name());
    }

    public Optional<User> findByEposta (String ePosta) {
        return userRepository.findByEPosta(ePosta);
    }
}
