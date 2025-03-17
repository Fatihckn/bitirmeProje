package com.bitirmeproje.controller;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.service.yeniyorum.IYeniYorumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yorum")
public class YeniYorumController {

    private final IYeniYorumService yeniYorumService;

    public YeniYorumController(IYeniYorumService yeniYorumService) {
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
}