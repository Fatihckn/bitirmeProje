package com.bitirmeproje.controller;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;
import com.bitirmeproje.helper.user.RequireUserAccess;
import com.bitirmeproje.service.AramaGecmisiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/arama")
public class AramaGecmisiController {
    private final AramaGecmisiService aramaGecmisiService;

    public AramaGecmisiController(AramaGecmisiService aramaGecmisiService) {
        this.aramaGecmisiService = aramaGecmisiService;
    }

    // Yeni arama kaydetme
    @PostMapping("/yeni")
    public ResponseEntity<String> AramaKaydet(@RequestBody AramaGecmisiDto aramaGecmisiDto, Authentication authentication) {
        aramaGecmisiService.AramaKaydet(aramaGecmisiDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Arama geçmişi kaydedildi.");
    }

    // Kullanıcının tüm arama geçmişini getirme
    @RequireUserAccess
    @GetMapping("/gecmis/{kullaniciId}")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisi(@PathVariable int kullaniciId) {

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisi(kullaniciId);
        return ResponseEntity.ok(aramaGecmisiList);
    }

    // Kullanıcının belirli tarih aralığındaki aramalarını getirme
    @RequireUserAccess
    @GetMapping("/gecmis/{kullaniciId}/tarih")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisiByDate(
            @PathVariable int kullaniciId,
            @RequestParam("baslangic") String baslangic,
            @RequestParam("bitis") String bitis) {

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisiByDate(
                kullaniciId, LocalDate.parse(baslangic), LocalDate.parse(bitis));

        return ResponseEntity.ok(aramaGecmisiList);
    }

    // Arama geçmişini silme
    @DeleteMapping("/{aramaGecmisiId}")
    public ResponseEntity<String> deleteArama(@PathVariable int aramaGecmisiId, Authentication authentication) {
        aramaGecmisiService.deleteArama(aramaGecmisiId, authentication.getName());
        return ResponseEntity.ok("Arama Gecmisi Silindi");
    }

    // En çok yapılan aramaları getir
    @GetMapping("/populer")
    public ResponseEntity<List<Object[]>> getPopulerAramalar() {
        List<Object[]> populerAramalar = aramaGecmisiService.getPopulerAramalar();
        return ResponseEntity.ok(populerAramalar);
    }
}
