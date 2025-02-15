package com.bitirmeproje.service;

import com.bitirmeproje.dto.LoginRequest;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KullaniciService {


    private final KullaniciRepository kullaniciRepository;


    public KullaniciService(KullaniciRepository kullaniciRepository) {

        this.kullaniciRepository = kullaniciRepository;
    }

    public String login(LoginRequest request) {
        System.out.println("23. satıra girildi");
        Optional<User> userOptional = kullaniciRepository.findByKullaniciEPosta(request.getKullaniciEPosta());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Şifreyi doğrudan karşılaştırıyoruz (GÜVENLİ DEĞİL ama şimdilik böyle kullanıyoruz)
            if (user.getKullaniciSifre().equals(request.getKullaniciSifre())) {
                return "Giriş başarılı!";
            }
        }

        return "Geçersiz e-posta veya şifre!";
    }
}
