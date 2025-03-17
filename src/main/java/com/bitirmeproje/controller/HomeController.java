package com.bitirmeproje.controller;

import com.bitirmeproje.dto.home.HomeDto;
import com.bitirmeproje.service.home.IHomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final IHomeService homeService;

    public HomeController(IHomeService homeService) {
        this.homeService = homeService;
    }

    // Kullanıcının takip ettiği kişilerin gönderilerini getir.
    @GetMapping("/home")
    public ResponseEntity<List<HomeDto>> home() {
        List<HomeDto> gonderiler = homeService.getHome();
        return new ResponseEntity<>(gonderiler, HttpStatus.OK);
    }
}
