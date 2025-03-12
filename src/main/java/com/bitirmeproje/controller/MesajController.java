package com.bitirmeproje.controller;

import com.bitirmeproje.dto.mesaj.MesajCreateDto;
import com.bitirmeproje.dto.mesaj.MesajDto;
import com.bitirmeproje.dto.mesaj.MesajUpdateDto;
import com.bitirmeproje.service.IMesajService;
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
    public ResponseEntity<MesajDto> mesajGonder(@RequestBody MesajCreateDto mesajCreateDto) {
        MesajDto mesajDto = mesajService.mesajGonder(mesajCreateDto);
        return ResponseEntity.ok(mesajDto);
    }

    // Gelen mesajları listeleme
    @GetMapping("/gelen/{kullaniciId}")
    public ResponseEntity<List<MesajDto>> gelenMesajlariListele(@PathVariable int kullaniciId) {
        return ResponseEntity.ok(mesajService.gelenMesajlariListele(kullaniciId));
    }

    // Gönderilen mesajları listeleme
    @GetMapping("/gonderilen/{kullaniciId}")
    public ResponseEntity<List<MesajDto>> gonderilenMesajlariListele(@PathVariable int kullaniciId) {
        return ResponseEntity.ok(mesajService.gonderilenMesajlariListele(kullaniciId));
    }

    // Belirli bir mesajı getir
    @GetMapping("/{mesajId}")
    public ResponseEntity<Optional<MesajDto>> mesajGetir(@PathVariable int mesajId) {
        return ResponseEntity.ok(mesajService.mesajGetir(mesajId));
    }

    // İki kullanıcı arasındaki sohbet geçmişini getir
    @GetMapping("/sohbet/{kullanici1Id}/{kullanici2Id}")
    public ResponseEntity<List<MesajDto>> sohbetGecmisiGetir(@PathVariable int kullanici1Id, @PathVariable int kullanici2Id) {
        return ResponseEntity.ok(mesajService.sohbetGecmisiGetir(kullanici1Id, kullanici2Id));
    }

    // Mesaj içeriğini güncelle
    @PutMapping("/{mesajId}/duzenle")
    public ResponseEntity<String> mesajGuncelle(@PathVariable int mesajId, @RequestBody MesajUpdateDto mesajUpdateDto) {
        mesajService.mesajGuncelle(mesajId, mesajUpdateDto);
        return ResponseEntity.ok("Mesaj başarıyla güncellendi.");
    }


    // Belirli bir mesajı sil
    @DeleteMapping("sil/{mesajId}")
    public ResponseEntity<String> mesajSil(@PathVariable int mesajId) {
        mesajService.mesajSil(mesajId);
        return ResponseEntity.ok("Mesaj başarıyla silindi.");
    }

    // İki kullanıcı arasındaki tüm mesajları sil
    @DeleteMapping("/sohbet/{kullanici1Id}/{kullanici2Id}")
    public ResponseEntity<String> tumMesajlariSil(@PathVariable int kullanici1Id, @PathVariable int kullanici2Id) {
        mesajService.tumMesajlariSil(kullanici1Id, kullanici2Id);
        return ResponseEntity.ok("Belirtilen iki kullanıcı arasındaki tüm mesajlar silindi.");
    }
}
