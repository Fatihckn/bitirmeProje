package com.bitirmeproje.helper.user;

import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FindUserByEmail implements FindUser<String> {
    private final UserRepository userRepository;

    public FindUserByEmail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(String value) {
        return userRepository.findByEPosta(value)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!"));
    }
}

