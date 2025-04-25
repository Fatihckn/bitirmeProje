package com.bitirmeproje.controller;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;
import com.bitirmeproje.service.aramagecmisi.IAramaGecmisiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arama")
public class AramaGecmisiController {
    private final IAramaGecmisiService aramaGecmisiService;

    public AramaGecmisiController(IAramaGecmisiService aramaGecmisiService) {
        this.aramaGecmisiService = aramaGecmisiService;
    }

    // Yeni arama kaydetme
    @PostMapping("/yeni")
    public ResponseEntity<String> AramaKaydet(@RequestBody AramaGecmisiDto aramaGecmisiDto) {
        aramaGecmisiService.AramaKaydet(aramaGecmisiDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Arama geçmişi kaydedildi.");
    }

    // Kullanıcının tüm arama geçmişini getirme
    @GetMapping("/gecmis")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisi() {

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisi();
        return ResponseEntity.ok(aramaGecmisiList);
    }

    // Arama geçmişini silme
    @DeleteMapping("/{aramaGecmisiId}")
    public ResponseEntity<String> deleteArama(@PathVariable int aramaGecmisiId) {
        aramaGecmisiService.deleteArama(aramaGecmisiId);
        return ResponseEntity.ok("Arama Gecmisi Silindi");
    }
}
