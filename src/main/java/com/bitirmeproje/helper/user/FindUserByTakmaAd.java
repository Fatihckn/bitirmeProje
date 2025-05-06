package com.bitirmeproje.helper.user;

import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FindUserByTakmaAd implements FindUser<String> {
    private final UserRepository userRepository;

    public FindUserByTakmaAd(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(String value) {
        return userRepository.findByKullaniciTakmaAd(value)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!"));
    }
}
