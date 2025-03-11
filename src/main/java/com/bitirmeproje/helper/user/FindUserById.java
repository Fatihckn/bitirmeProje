package com.bitirmeproje.helper.user;

import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FindUserById implements FindUser<Integer> {
    private final UserRepository userRepository;

    public FindUserById(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(Integer value) {

        return userRepository.findById(value)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!"));
    }
}

