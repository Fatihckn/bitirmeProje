package com.bitirmeproje.controller;

import com.bitirmeproje.dto.anketler.AnketlerSaveDto;
import com.bitirmeproje.dto.anketler.GirisYapanKullaniciAnketDto;
import com.bitirmeproje.model.Anketler;
import com.bitirmeproje.service.anketler.AnketlerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anketler")
public class AnketlerController {
    private final AnketlerService anketlerService;

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
}
