package com.bitirmeproje.service;

import com.bitirmeproje.dto.UserDto;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User FindById(int id) {
        return userRepository.findById(id).orElse(null);
    }


    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(
                        user.getKullaniciId(),
                        user.getKullaniciTakmaAd(),
                        user.getePosta(),
                        user.getKullaniciBio(),
                        user.getKullaniciProfilResmi(),
                        user.getKullaniciTelefonNo(),
                        user.getKullaniciDogumTarihi(),
                        user.getKullaniciUyeOlmaTarihi()
                ))
                .toList();
    }

    public List<String> findAllByKullaniciTakmaAd() {
        return userRepository.findAllByKullaniciTakmaAd();
    }

    public List<String> findAllByDogumTarihi() {
        return userRepository.findAllByDogumTarihi();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByKullaniciTakmaAd(String kullaniciTakmaAd) {
        return userRepository.findByKullaniciTakmaAd(kullaniciTakmaAd);
    }
}
