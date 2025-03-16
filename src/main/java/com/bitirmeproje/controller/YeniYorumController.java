package com.bitirmeproje.controller;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.service.YeniYorumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yorum")
public class YeniYorumController {

    private final YeniYorumService yeniYorumService;

    public YeniYorumController(YeniYorumService yeniYorumService) {
        this.yeniYorumService = yeniYorumService;
    }

    @PostMapping("/ekle")
    public ResponseEntity<YeniYorum> yeniYorumEkle(@RequestBody YeniYorumDto yeniYorumDto) {
        YeniYorum yeniYorum = yeniYorumService.yeniYorumEkle(yeniYorumDto);
        return ResponseEntity.ok(yeniYorum);
    }

    @GetMapping("/gonderi/{gonderiId}") // Belirli bir Gönderiye yapılan yorumları getir
    public ResponseEntity<List<YeniYorumDto>> getYorumlarByGonderiId(@PathVariable int gonderiId) {
        List<YeniYorumDto> yorumlar = yeniYorumService.getYorumlarByGonderiId(gonderiId);
        return ResponseEntity.ok(yorumlar);
    }

    // **Yeni eklenen API**: Belirli bir kullanıcının yaptığı yorumları getir
    @GetMapping("/kullanici/{kullaniciId}")
    public ResponseEntity<List<YeniYorumDto>> getYorumlarByKullaniciId(@PathVariable int kullaniciId) {
        List<YeniYorumDto> yorumlar = yeniYorumService.getYorumlarByKullaniciId(kullaniciId);
        return ResponseEntity.ok(yorumlar);
    }
    @DeleteMapping("/{id}/yorumu-sil")
    public ResponseEntity<String> yorumSil(@PathVariable int id) {
        yeniYorumService.yorumuSil(id);
        return ResponseEntity.status(HttpStatus.OK).body("Yorum başarıyla silindi.");
    }
    @PostMapping("/{id}/begeni")
    public ResponseEntity<String> yorumBegen(@PathVariable("id") int yorumId) {
        yeniYorumService.yorumBegen(yorumId);
        return ResponseEntity.ok("Yorum başarıyla beğenildi.");
    }
    @DeleteMapping("/{id}/begeni-kaldir")
    public ResponseEntity<String> yorumBegeniCek(@PathVariable("id") int yorumId) {
        yeniYorumService.yorumBegeniCek(yorumId);

        return ResponseEntity.ok("Beğeni başarıyla kaldırıldı.");
    }
    @PostMapping("/{id}/yanit-ekle")
    public ResponseEntity<String> yorumaYanitEkle(@PathVariable("id") int yorumId,
                                                  @RequestBody YeniYorumDto yeniYorumDto) {
        yeniYorumService.yorumaYanitEkle(yorumId, yeniYorumDto);
        return ResponseEntity.ok("Yanıt başarıyla eklendi.");
    }
    @GetMapping("/{id}/yanitlar")
    public List<YeniYorumDto> getYanitlarByYorumId(@PathVariable int id) {
        return yeniYorumService.getYanitlarByYorumId(id);
    }
    @GetMapping("/{yorum_id}/begeni-sayisi")
    public ResponseEntity<Integer> getBegeniSayisi(@PathVariable int yorum_id) {
        int begeniSayisi = yeniYorumService.getBegeniSayisi(yorum_id);
        return ResponseEntity.ok(begeniSayisi);
    }
    @GetMapping("/kullanici/{kullaniciId}/begenilen-yorumlar")
    public List<YeniYorumDto> getBegenilenYorumlar(@PathVariable int kullaniciId) {
        return yeniYorumService.getBegenilenYorumlar(kullaniciId);
    }
}