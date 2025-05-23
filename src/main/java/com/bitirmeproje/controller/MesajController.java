package com.bitirmeproje.controller;

import com.bitirmeproje.dto.mesaj.*;
import com.bitirmeproje.service.mesaj.IMesajService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mesaj")
public class MesajController {

    private final IMesajService mesajService;

    public MesajController(IMesajService mesajService) {
        this.mesajService = mesajService;
    }

    // Yeni mesaj gönderme
    @PostMapping("/gönder")
    public ResponseEntity<MesajAtmaDto> mesajGonder(@RequestBody MesajCreateDto mesajCreateDto) {
        MesajAtmaDto mesajDto = mesajService.mesajGonder(mesajCreateDto);
        return ResponseEntity.ok(mesajDto);
    }

    // Gelen mesajları listeleme
    @GetMapping("/gelen")
    public ResponseEntity<List<MesajDto>> gelenMesajlariListele() {
        return ResponseEntity.ok(mesajService.gelenMesajlariListele());
    }

    // Gönderilen mesajları listeleme
    @GetMapping("/gonderilen")
    public ResponseEntity<List<MesajDto>> gonderilenMesajlariListele() {
        return ResponseEntity.ok(mesajService.gonderilenMesajlariListele());
    }

    // Belirli bir mesajı getir
    @GetMapping("/{mesajId}")
    public ResponseEntity<Optional<MesajDto>> mesajGetir(@PathVariable int mesajId) {
        return ResponseEntity.ok(mesajService.mesajGetir(mesajId));
    }

    // İki kullanıcı arasındaki sohbet geçmişini getir
    @GetMapping("/sohbet/{kullaniciId}")
    public ResponseEntity<List<MesajSohbetGecmisiGetirDto>> sohbetGecmisiGetir(@PathVariable int kullaniciId) {
        return ResponseEntity.ok(mesajService.sohbetGecmisiGetir(kullaniciId));
    }

    // Mesaj içeriğini güncelle
    @PutMapping("/{mesajId}/duzenle")
    public ResponseEntity<String> mesajGuncelle(@PathVariable int mesajId, @RequestBody MesajDto mesajDto) {
        mesajService.mesajGuncelle(mesajId, mesajDto);
        return ResponseEntity.ok("Mesaj başarıyla güncellendi.");
    }


    // Belirli bir mesajı sil
    @DeleteMapping("sil/{mesajId}")
    public ResponseEntity<String> mesajSil(@PathVariable int mesajId) {
        mesajService.mesajSil(mesajId);
        return ResponseEntity.ok("Mesaj başarıyla silindi.");
    }

    // İki kullanıcı arasındaki tüm mesajları sil
    @DeleteMapping("/sohbet/{kullaniciId}")
    public ResponseEntity<String> tumMesajlariSil(@PathVariable int kullaniciId) {
        mesajService.tumMesajlariSil(kullaniciId);
        return ResponseEntity.ok("Belirtilen iki kullanıcı arasındaki tüm mesajlar silindi.");
    }

    @GetMapping("/kullanici-son-sohbetler")
    public ResponseEntity<List<KullanicininSonGelenMesajlari>> kullaniciSonSohbetler() {
        return ResponseEntity.ok(mesajService.getKullanicininSonGelenMesajlari());
    }

    @PutMapping("/update-mesaj-okundu-mu/{kullaniciId}")
    public ResponseEntity<String> mesajOkunduMu(@PathVariable int kullaniciId) {
        mesajService.updateMesajOkunduMu(kullaniciId);
        return ResponseEntity.ok("Mesaj Okundu Bilgisi Güncellendi.");
    }
}
