package com.bitirmeproje.controller;

import com.bitirmeproje.dto.FollowsDto;
import com.bitirmeproje.dto.PopulerKullaniciDto;
import com.bitirmeproje.model.Follows;
import com.bitirmeproje.service.FollowsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
public class FollowsController {
    private final FollowsService followsService;
    public FollowsController(FollowsService followsService)
    {
        this.followsService=followsService;
    }

    @GetMapping("/populer")
    public ResponseEntity<List<PopulerKullaniciDto>> populerKullanicilariGetir() {
        List<PopulerKullaniciDto> populerKullanicilar = followsService.populerKullanicilariGetir();
        return ResponseEntity.ok(populerKullanicilar);
    }


}
