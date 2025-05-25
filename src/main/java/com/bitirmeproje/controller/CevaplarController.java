package com.bitirmeproje.controller;

import com.bitirmeproje.service.cevaplar.ICevaplarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cevaplar")
public class CevaplarController {
    private final ICevaplarService cevaplarService;

    public CevaplarController(ICevaplarService cevaplarService) {
        this.cevaplarService = cevaplarService;
    }

    @PostMapping("/save/{anketId}/{secenekId}")
    public ResponseEntity<String> saveCevap(@PathVariable int anketId, @PathVariable int secenekId) {
        cevaplarService.cevapKaydet(anketId, secenekId);
        return ResponseEntity.ok("Cevap kaydedildi");
    }
}
