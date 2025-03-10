package com.bitirmeproje.helper;

import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserIdControl {
    private final UserRepository userRepository;

    public UserIdControl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(int id) {
        Optional<User> kullanici = userRepository.findByKullaniciId(id);

        if (kullanici.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanici Bulunamadi");
        }

        return kullanici.get();
    }
}
