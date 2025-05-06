package com.bitirmeproje.controller;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderiEkleDto;
import com.bitirmeproje.service.gonderiler.IGonderilerService;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/ekle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> yeniGonderiEkle(@ModelAttribute GonderiEkleDto gonderiEkleDto) {
        gonderilerService.yeniGonderiEkle(gonderiEkleDto);
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

    @GetMapping("/aranan-gonderi/{gonderiId}")
    public ResponseEntity<GonderiDto> arananGonderiGetir(@PathVariable int gonderiId) {
        return ResponseEntity.ok(gonderilerService.getArananGonderi(gonderiId));
    }
}

