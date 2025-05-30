package com.bitirmeproje.controller;

import com.bitirmeproje.dto.anketler.AnketOneriDto;
import com.bitirmeproje.dto.anketler.AnketlerSaveDto;
import com.bitirmeproje.dto.anketler.GirisYapanKullaniciAnketDto;
import com.bitirmeproje.dto.anketler.KullaniciCevapladigiAnketlerDto;
import com.bitirmeproje.model.Anketler;
import com.bitirmeproje.service.anketler.AnketlerService;
import com.bitirmeproje.service.anketler.IAnketlerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anketler")
public class AnketlerController {
    private final IAnketlerService anketlerService;

    public AnketlerController(AnketlerService anketlerService) {
        this.anketlerService = anketlerService;
    }

    @PostMapping("/save")
    public ResponseEntity<Anketler> saveAnketler(@RequestBody AnketlerSaveDto anketlerSaveDto) {
        return ResponseEntity.ok(anketlerService.anketlerSave(anketlerSaveDto));
    }

    @GetMapping("/kullanici-anket-getir")
    public ResponseEntity<List<GirisYapanKullaniciAnketDto>> getKullaniciAnketlerGetir() {
        return ResponseEntity.ok(anketlerService.getKullaniciAnketler());
    }

    @DeleteMapping("/delete/{anketId}")
    public ResponseEntity<String> deleteAnket(@PathVariable int anketId) {
        anketlerService.deleteAnket(anketId);
        return ResponseEntity.ok("Anket Başariyla Silindi");
    }

    @GetMapping("/anket-oneri")
    public ResponseEntity<List<AnketOneriDto>> anketOneri(){
        return ResponseEntity.ok(anketlerService.kullaniciAnketOneri());
    }

    @GetMapping("/cevaplanan/anketler")
    public ResponseEntity<List<KullaniciCevapladigiAnketlerDto>> cevaplananAnketler(){
        return ResponseEntity.ok(anketlerService.getKullaniciCevapladigiAnketler());
    }
}
