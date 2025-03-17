package com.bitirmeproje.controller;

import com.bitirmeproje.dto.follows.PopulerKullaniciDto;
import com.bitirmeproje.service.follows.IFollowsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
public class FollowsController {
    private final IFollowsService followsService;
    public FollowsController(IFollowsService followsService)
    {
        this.followsService=followsService;
    }

    @GetMapping("/populer")
    public ResponseEntity<List<PopulerKullaniciDto>> populerKullanicilariGetir() {
        List<PopulerKullaniciDto> populerKullanicilar = followsService.populerKullanicilariGetir();
        return ResponseEntity.ok(populerKullanicilar);
    }


}
