package com.bitirmeproje.controller;

import com.bitirmeproje.dto.AramaGecmisiDto;
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
    private final UserRepository userRepository;

    public AramaGecmisiController(AramaGecmisiService aramaGecmisiService, UserRepository userRepository) {
        this.aramaGecmisiService = aramaGecmisiService;
        this.userRepository = userRepository;
    }

    // Yeni arama kaydetme
    @PostMapping("/yeni")
    public ResponseEntity<AramaGecmisi> AramaKaydet(@RequestBody AramaGecmisi aramaGecmisi) {
        AramaGecmisi yeniArama = aramaGecmisiService.AramaKaydet(aramaGecmisi);
        return ResponseEntity.status(HttpStatus.CREATED).body(yeniArama);
    }

    // Kullanıcının tüm arama geçmişini getirme
    @GetMapping("/gecmis/{kullaniciId}")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisi(@PathVariable int kullaniciId) {
        Optional<User> kullaniciOpt = userRepository.findById(kullaniciId);

        if (kullaniciOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisi(kullaniciOpt.get());
        return ResponseEntity.ok(aramaGecmisiList);
    }

    // Kullanıcının belirli tarih aralığındaki aramalarını getirme
    @GetMapping("/gecmis/{kullaniciId}/tarih")
    public ResponseEntity<List<AramaGecmisiDto>> getKullaniciAramaGecmisiByDate(
            @PathVariable int kullaniciId,
            @RequestParam("baslangic") String baslangic,
            @RequestParam("bitis") String bitis) {

        Optional<User> kullaniciOpt = userRepository.findById(kullaniciId);
        if (kullaniciOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<AramaGecmisiDto> aramaGecmisiList = aramaGecmisiService.getKullaniciAramaGecmisiByDate(
                kullaniciOpt.get(), LocalDate.parse(baslangic), LocalDate.parse(bitis));

        return ResponseEntity.ok(aramaGecmisiList);
    }

    // Arama geçmişini silme
    @DeleteMapping("/{aramaGecmisiId}")
    public ResponseEntity<Void> deleteArama(@PathVariable int aramaGecmisiId) {
        aramaGecmisiService.deleteArama(aramaGecmisiId);
        return ResponseEntity.noContent().build();
    }

    // En çok yapılan aramaları getir
    @GetMapping("/populer")
    public ResponseEntity<List<Object[]>> getPopulerAramalar() {
        List<Object[]> populerAramalar = aramaGecmisiService.getPopulerAramalar();
        return ResponseEntity.ok(populerAramalar);
    }
}
