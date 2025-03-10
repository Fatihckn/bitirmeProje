package com.bitirmeproje.controller;

import com.bitirmeproje.model.BegenilenGonderiler;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.service.BegenilenGonderilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bitirmeproje.dto.BegenilenGonderilerDto;
import java.util.List;

@RestController
@RequestMapping("/api/gonderi")
public class BegenilenGonderilerController {
    private final BegenilenGonderilerService service;
    private final UserRepository userRepository; // Kullanıcıyı veritabanından almak için repo

    public BegenilenGonderilerController(BegenilenGonderilerService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @PostMapping("/{gonderi_id}/begeni") // Gonderiyi begen
    public ResponseEntity<String> begeniEkle(@PathVariable("gonderi_id") int gonderiId, @RequestBody User kullanici) {
        if (kullanici.getKullaniciId() == 0) {
            return ResponseEntity.badRequest().body("Kullanıcı ID eksik!");
        }

        // Kullanıcı veritabanında var mı kontrol et
        User dbKullanici = userRepository.findById(kullanici.getKullaniciId()).orElse(null);
        if (dbKullanici == null) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı!");
        }

        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiId(gonderiId); // ID ile gönderi nesnesi oluştur
        service.begeniEkle(gonderi, dbKullanici);
        return ResponseEntity.ok("Begeni eklendi");
    }

    @DeleteMapping("/{gonderi_id}/begeni") // Gonderiden begeni kaldır
    public ResponseEntity<String> begeniKaldir(@PathVariable("gonderi_id") int gonderiId, @RequestBody User kullanici) {
        if (kullanici.getKullaniciId() == 0) {
            return ResponseEntity.badRequest().body("Kullanıcı ID eksik!");
        }

        User dbKullanici = userRepository.findById(kullanici.getKullaniciId()).orElse(null);
        if (dbKullanici == null) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı!");
        }

        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiId(gonderiId);
        service.begeniKaldir(gonderi, dbKullanici);
        return ResponseEntity.ok("Begeni kaldirildi");
    }

    @GetMapping("/{gonderi_id}/begeni-sayisi") // Belirli bir gönderinin beğeni sayısını getir
    public ResponseEntity<Integer> begeniSayisi(@PathVariable("gonderi_id") int gonderiId) {
        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiId(gonderiId);
        return ResponseEntity.ok(service.gonderiBegeniSayisi(gonderi));
    }

    @GetMapping("/kullanici/{kullanici_id}/begenilen-gonderiler")
    public ResponseEntity<List<BegenilenGonderilerDto>> kullanicininBegenileri(@PathVariable("kullanici_id") int kullaniciId) {
        User dbKullanici = userRepository.findById(kullaniciId).orElse(null);
        if (dbKullanici == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(service.kullanicininBegenileri(dbKullanici));
    }

    /*@GetMapping("/populer") // En popüler gönderileri listele
    public ResponseEntity<List<BegenilenGonderilerDto>> populerGonderiler() {
        return ResponseEntity.ok(service.populerGonderiler());
    }*/
}
