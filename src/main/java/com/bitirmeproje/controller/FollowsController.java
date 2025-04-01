package com.bitirmeproje.controller;

import com.bitirmeproje.dto.follows.PopulerKullaniciDto;
import com.bitirmeproje.service.follows.IFollowsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follows")
public class FollowsController {
    private final IFollowsService followsService;
    public FollowsController(IFollowsService followsService)
    {
        this.followsService=followsService;
    }

    // Bir kullanıcıyı takip et
    @PostMapping("/takip-et")
    public ResponseEntity<String> followUser(@RequestParam int takipEdilenId) {

        followsService.followUser(takipEdilenId);
        return ResponseEntity.ok("Kullanıcı başarıyla takip edildi.");
    }

    // Bir kullanıcıyı takipten çık
    @DeleteMapping("/takibi-bırakma")
    public ResponseEntity<String> unfollowUser(@RequestParam int takipEdilenId) {

        followsService.unfollowUser(takipEdilenId);
        return ResponseEntity.ok("Kullanıcı başarıyla takipten çıkıldı");
    }

    // Kullanıcının takipçilerini getir
    @GetMapping("/takipciler")
    public ResponseEntity <Map<String, Object>> getFollowers() {
        Map<String, Object> followers = followsService.getFollowers();
        return ResponseEntity.ok(followers);
    }

    // Kullanıcının takip ettiği kişileri getir
    @GetMapping("/takip-edilenler")
    public ResponseEntity<Map<String, Object>> getFollowing() {
        Map<String, Object> following = followsService.getFollowing();
        return ResponseEntity.ok(following);
    }

    @GetMapping("/populer")
    public ResponseEntity<List<PopulerKullaniciDto>> populerKullanicilariGetir() {
        List<PopulerKullaniciDto> populerKullanicilar = followsService.populerKullanicilariGetir();
        return ResponseEntity.ok(populerKullanicilar);
    }
}
