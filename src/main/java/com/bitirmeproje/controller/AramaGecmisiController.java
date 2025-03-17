package com.bitirmeproje.controller;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;
import com.bitirmeproje.service.aramagecmisi.IAramaGecmisiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    // Kullanıcının belirli tarih aralığındaki aramalarını getirme
    @GetMapping("/gecmis/tarih")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisiByDate(
            @RequestParam("baslangic") String baslangic,
            @RequestParam("bitis") String bitis) {

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisiByDate(LocalDate.parse(baslangic), LocalDate.parse(bitis));

        return ResponseEntity.ok(aramaGecmisiList);
    }

    // Arama geçmişini silme
    @DeleteMapping("/{aramaGecmisiId}")
    public ResponseEntity<String> deleteArama(@PathVariable int aramaGecmisiId) {
        aramaGecmisiService.deleteArama(aramaGecmisiId);
        return ResponseEntity.ok("Arama Gecmisi Silindi");
    }

    // En çok yapılan aramaları getir
    @GetMapping("/populer")
    public ResponseEntity<List<Object[]>> getPopulerAramalar() {
        List<Object[]> populerAramalar = aramaGecmisiService.getPopulerAramalar();
        return ResponseEntity.ok(populerAramalar);
    }
}
