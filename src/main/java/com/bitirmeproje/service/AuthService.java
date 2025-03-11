package com.bitirmeproje.service;

import com.bitirmeproje.dto.auth.LoginDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.Role;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.security.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final FindUser<String> findUser;

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, FindUser<String> findUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.findUser = findUser;
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
        User user = findUser.findUser(loginDto.getePosta());

        if (!passwordEncoder.matches(loginDto.getSifre(), user.getSifre())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,"Hatalı şifre!");
        }

        // Başarılı giriş, token üret ve geri döndür
        return jwtUtil.generateToken(user.getePosta(), user.getKullaniciRole().name());
    }
}
