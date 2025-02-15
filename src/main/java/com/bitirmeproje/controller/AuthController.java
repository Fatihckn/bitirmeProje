package com.bitirmeproje.controller;

import com.bitirmeproje.dto.LoginRequest;
import com.bitirmeproje.service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final KullaniciService kullaniciService;

    public AuthController(KullaniciService kullaniciService) {
        System.out.println("20. satıra girildi");
        this.kullaniciService = kullaniciService;
        System.out.println("21. satıra girildi");
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) {
        String response = kullaniciService.login(request);
        System.out.println(request.getKullaniciEPosta());
        System.out.println(request.getKullaniciSifre());
//        return ResponseEntity.ok(response);
    }
}
