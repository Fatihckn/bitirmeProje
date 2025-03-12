package com.bitirmeproje.controller;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;
import com.bitirmeproje.security.jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;

    public AramaGecmisiController(AramaGecmisiService aramaGecmisiService,
                                  JwtUtil jwtUtil) {
        this.aramaGecmisiService = aramaGecmisiService;
        this.jwtUtil = jwtUtil;
    }

    // Yeni arama kaydetme
    @PostMapping("/yeni")
    public ResponseEntity<String> AramaKaydet(@RequestBody AramaGecmisiDto aramaGecmisiDto, Authentication authentication) {
        aramaGecmisiService.AramaKaydet(aramaGecmisiDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Arama geçmişi kaydedildi.");
    }

    // Kullanıcının tüm arama geçmişini getirme
    @GetMapping("/gecmis")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisi() {

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisi(jwtUtil.extractUserId());
        return ResponseEntity.ok(aramaGecmisiList);
    }

    // Kullanıcının belirli tarih aralığındaki aramalarını getirme
    @GetMapping("/gecmis/tarih")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisiByDate(
            @RequestParam("baslangic") String baslangic,
            @RequestParam("bitis") String bitis) {

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisiByDate(
                jwtUtil.extractUserId(), LocalDate.parse(baslangic), LocalDate.parse(bitis));

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
