package com.bitirmeproje.controller;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderilerAllDto;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.service.begenilengonderiler.IBegenilenGonderilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bitirmeproje.dto.begenilengonderiler.BegenilenGonderilerDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gonderiBegeni")
public class BegenilenGonderilerController {
    private final IBegenilenGonderilerService begenilenGonderilerService;

    public BegenilenGonderilerController(IBegenilenGonderilerService begenilenGonderilerService) {
        this.begenilenGonderilerService = begenilenGonderilerService;
    }

    @PostMapping("/{gonderi_id}/begeni")
    public ResponseEntity<String> begeniEkle(@PathVariable("gonderi_id") int gonderiId) {
        // Beğeni işlemini Service katmanına yönlendir
        begenilenGonderilerService.begeniEkle(gonderiId);

        return ResponseEntity.ok("Beğeni eklendi!");
    }

    @DeleteMapping("/{gonderi_id}/begeni-kaldir") // Gonderiden begeni kaldır
    public ResponseEntity<String> begeniKaldir(@PathVariable("gonderi_id") int gonderiId) {

        begenilenGonderilerService.begeniKaldir(gonderiId);
        return ResponseEntity.ok("Begeni kaldirildi");
    }

//    @GetMapping("/{gonderi_id}/begeni-sayisi") // Belirli bir gönderinin beğeni sayısını getir
//    public ResponseEntity<Integer> begeniSayisi(@PathVariable("gonderi_id") int gonderiId) {
//
//        return ResponseEntity.ok(begenilenGonderilerService.gonderiBegeniSayisi(gonderiId));
//    }

    @GetMapping("/{gonderi_id}/begeni-sayisi") // Belirli bir gönderinin beğeni sayısını getir
    public ResponseEntity<Map<String, Integer>> begeniSayisi(@PathVariable("gonderi_id") int gonderiId) {
        int begeniSayisi = begenilenGonderilerService.gonderiBegeniSayisi(gonderiId);

        // JSON formatında dönmek için Map kullan
        Map<String, Integer> response = new HashMap<>();
        response.put("begeni_sayisi", begeniSayisi);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/begeni-sayisi-all") // Belirli bir gönderinin beğeni sayısını getir
    public ResponseEntity<List<GonderilerAllDto>> begeniSayisiAll() {
        return ResponseEntity.ok(begenilenGonderilerService.gonderiBegeniSayisiAll());
    }

    @GetMapping("/kullanici/begenilen-gonderiler")
    public ResponseEntity<List<BegenilenGonderilerDto>> kullanicininBegenileri() {

        return ResponseEntity.ok(begenilenGonderilerService.kullanicininBegenileri());
    }
}
