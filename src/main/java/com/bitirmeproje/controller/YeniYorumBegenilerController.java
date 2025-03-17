package com.bitirmeproje.controller;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.service.yeniyorumbegeniler.IYeniYorumBegenilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yorumBegeniler")
public class YeniYorumBegenilerController {
    private final IYeniYorumBegenilerService yeniYorumBegenilerService;

    public YeniYorumBegenilerController(IYeniYorumBegenilerService yeniYorumBegenilerService) {
        this.yeniYorumBegenilerService = yeniYorumBegenilerService;
    }

    @PostMapping("/{id}/begeni")
    public ResponseEntity<String> yorumBegen(@PathVariable("id") int yorumId) {
        yeniYorumBegenilerService.yorumBegen(yorumId);
        return ResponseEntity.ok("Yorum başarıyla beğenildi.");
    }

    @DeleteMapping("/{id}/begeni-kaldir")
    public ResponseEntity<String> yorumBegeniCek(@PathVariable("id") int yorumId) {
        yeniYorumBegenilerService.yorumBegeniCek(yorumId);

        return ResponseEntity.ok("Beğeni başarıyla kaldırıldı.");
    }

    @GetMapping("/{yorum_id}/begeni-sayisi")
    public ResponseEntity<Integer> getBegeniSayisi(@PathVariable int yorum_id) {
        int begeniSayisi = yeniYorumBegenilerService.getBegeniSayisi(yorum_id);
        return ResponseEntity.ok(begeniSayisi);
    }

    @GetMapping("/kullanici/{kullaniciId}/begenilen-yorumlar")
    public List<YeniYorumDto> getBegenilenYorumlar(@PathVariable int kullaniciId) {
        return yeniYorumBegenilerService.getBegenilenYorumlar(kullaniciId);
    }
}
