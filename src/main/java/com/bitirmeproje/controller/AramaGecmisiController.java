package com.bitirmeproje.controller;

import com.bitirmeproje.dto.AramaGecmisiDto;
import com.bitirmeproje.helper.RequireUserAccess;
import com.bitirmeproje.helper.UserIdControl;
import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.service.AramaGecmisiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/arama")
public class AramaGecmisiController {
    private final AramaGecmisiService aramaGecmisiService;

    public AramaGecmisiController(AramaGecmisiService aramaGecmisiService) {
        this.aramaGecmisiService = aramaGecmisiService;
    }

    // Yeni arama kaydetme
    @PostMapping("/yeni")
    public ResponseEntity<String> AramaKaydet(@RequestBody AramaGecmisiDto aramaGecmisi) {
        aramaGecmisiService.AramaKaydet(aramaGecmisi);
        return ResponseEntity.status(HttpStatus.CREATED).body("Arama geçmişi kaydedildi");
    }

    // Kullanıcının tüm arama geçmişini getirme
    @GetMapping("/gecmis/{kullaniciId}")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisi(@PathVariable int kullaniciId) {

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisi(kullaniciId);
        return ResponseEntity.ok(aramaGecmisiList);
    }

    // Kullanıcının belirli tarih aralığındaki aramalarını getirme
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
