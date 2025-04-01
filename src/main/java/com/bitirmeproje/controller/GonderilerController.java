package com.bitirmeproje.controller;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.service.gonderiler.IGonderilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gonderi")
public class GonderilerController {

    private final IGonderilerService gonderilerService;

    public GonderilerController(IGonderilerService gonderilerService) {
        this.gonderilerService = gonderilerService;
    }

    @GetMapping("/kullanici/gonderiler")
    public ResponseEntity<List<GonderiDto>> kullaniciGonderileriniGetir() {
        List<GonderiDto> gonderiler = gonderilerService.kullaniciGonderileriniGetir();
        return ResponseEntity.ok(gonderiler);
    }

    @PostMapping("/ekle")
    public ResponseEntity<String> yeniGonderiEkle(@RequestBody GonderiDto gonderiDto) {
        gonderilerService.yeniGonderiEkle(gonderiDto);
        return ResponseEntity.ok("Gönderi başarıyla oluşturuldu.");
    }

    @DeleteMapping("/sil/{gonderiId}")
    public ResponseEntity<String> gonderiSil(@PathVariable int gonderiId) {
        gonderilerService.gonderiSil(gonderiId);
        return ResponseEntity.ok("Gönderi başarıyla silindi.");
    }

    @GetMapping("/populer")
    public ResponseEntity<List<GonderiDto>> populerGonderileriGetir() {
        List<GonderiDto> populerGonderiler = gonderilerService.populerGonderileriGetir();
        return ResponseEntity.ok(populerGonderiler);
    }

    @PutMapping("/guncelle/{gonderiId}")
    public ResponseEntity<String> gonderiGuncelle(@PathVariable int gonderiId, @RequestBody GonderiDto gonderiDto) {
        gonderilerService.gonderiGuncelle(gonderiId, gonderiDto.getGonderiIcerigi());
        return ResponseEntity.ok("Gönderi başarıyla güncellendi.");
    }

/*    // Gönderiye yorum yapma
    @PostMapping("/{id}/yorum")
    public ResponseEntity<String> yorumEkle(@PathVariable int id, @RequestParam String yorum) {
        gonderilerService.yorumEkle(id, yorum);
        return ResponseEntity.ok("Yorum başarıyla eklendi.");
    }

    // Gönderinin yorumlarını listeleme
    @GetMapping("/{id}/yorumlar")
    public ResponseEntity<List<String>> yorumlariListele(@PathVariable int id) {
        List<String> yorumlar = gonderilerService.yorumlariListele(id);
        return ResponseEntity.ok(yorumlar);
    }

    // Gönderiye emoji ile tepki verme
    @PostMapping("/{id}/tepki")
    public ResponseEntity<String> tepkiVer(@PathVariable int id, @RequestParam String emoji) {
        gonderilerService.tepkiVer(id, emoji);
        return ResponseEntity.ok("Emoji ile tepki verildi.");
    }
*/

}

