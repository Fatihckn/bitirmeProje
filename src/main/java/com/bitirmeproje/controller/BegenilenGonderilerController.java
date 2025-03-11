package com.bitirmeproje.controller;

import com.bitirmeproje.service.BegenilenGonderilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.bitirmeproje.dto.begenilengonderiler.BegenilenGonderilerDto;
import java.util.List;

@RestController
@RequestMapping("/api/gonderiBegeni")
public class BegenilenGonderilerController {
    private final BegenilenGonderilerService begenilenGonderilerService;

    public BegenilenGonderilerController(BegenilenGonderilerService begenilenGonderilerService) {
        this.begenilenGonderilerService = begenilenGonderilerService;
    }

    @PostMapping("/{gonderi_id}/begeni")
    public ResponseEntity<String> begeniEkle(@PathVariable("gonderi_id") int gonderiId,
                                             Authentication authentication) {
        // Beğeni işlemini Service katmanına yönlendir
        begenilenGonderilerService.begeniEkle(gonderiId, authentication.getName());

        return ResponseEntity.ok("Beğeni eklendi!");
    }

    @DeleteMapping("/{gonderi_id}/begeni-kaldir") // Gonderiden begeni kaldır
    public ResponseEntity<String> begeniKaldir(@PathVariable("gonderi_id") int gonderiId,
                                               Authentication authentication) {

        begenilenGonderilerService.begeniKaldir(gonderiId, authentication.getName());
        return ResponseEntity.ok("Begeni kaldirildi");
    }

    @GetMapping("/{gonderi_id}/begeni-sayisi") // Belirli bir gönderinin beğeni sayısını getir
    public ResponseEntity<String> begeniSayisi(@PathVariable("gonderi_id") int gonderiId) {

        return ResponseEntity.ok("Gönderinin Beğeni sayısı: " + begenilenGonderilerService.gonderiBegeniSayisi(gonderiId));
    }

    @GetMapping("/kullanici/begenilen-gonderiler")
    public ResponseEntity<List<BegenilenGonderilerDto>> kullanicininBegenileri(Authentication authentication) {

        return ResponseEntity.ok(begenilenGonderilerService.kullanicininBegenileri(authentication.getName()));
    }
}
