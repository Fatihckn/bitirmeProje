package com.bitirmeproje.controller;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderiResponseDto;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.service.GonderilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gonderi")
public class GonderilerController {

    private final GonderilerService gonderilerService;

    public GonderilerController(GonderilerService gonderilerService) {
        this.gonderilerService = gonderilerService;
    }

    @GetMapping("/kullanici/{id}/gonderiler")
    public ResponseEntity<List<Gonderiler>> kullaniciGonderileriniGetir(@PathVariable int id) {
        List<Gonderiler> gonderiler = gonderilerService.kullaniciGonderileriniGetir(id);
        return ResponseEntity.ok(gonderiler);
    }

    @PostMapping("/ekle")
    public ResponseEntity<String> yeniGonderiEkle(@RequestBody GonderiDto gonderiDto, @RequestParam int kullaniciId) {
        gonderilerService.yeniGonderiEkle(kullaniciId, gonderiDto);
        return ResponseEntity.ok("Gönderi başarıyla oluşturuldu.");
    }

    @DeleteMapping("/sil/{gonderiId}")
    public ResponseEntity<String> gonderiSil(@PathVariable int gonderiId ) {
        gonderilerService.gonderiSil(gonderiId);
        return ResponseEntity.ok("Gönderi başarıyla silindi.");
    }

    @PostMapping("/begeni/ekle/{gonderiId}")
    public ResponseEntity<String> begeniEkle(@PathVariable int gonderiId) {
        gonderilerService.begeniEkle(gonderiId);
        return ResponseEntity.ok("Beğeni başarıyla eklendi.");
    }

    @DeleteMapping("/begeni/sil/{gonderiId}")
    public ResponseEntity<String> begeniKaldir(@PathVariable int gonderiId) {
        gonderilerService.begeniKaldir(gonderiId);
        return ResponseEntity.ok("Beğeni başarıyla kaldırıldı.");
    }

    @GetMapping("/populer")
    public ResponseEntity<List<GonderiResponseDto>> populerGonderileriGetir() {
        List<GonderiResponseDto> populerGonderiler = gonderilerService.populerGonderileriGetir();
        return ResponseEntity.ok(populerGonderiler);
    }

    @PutMapping("/guncelle/{id}")
    public ResponseEntity<String> gonderiGuncelle(@PathVariable int id, @RequestBody GonderiDto gonderiDto) {
        gonderilerService.gonderiGuncelle(id, gonderiDto.getGonderiIcerigi());
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

