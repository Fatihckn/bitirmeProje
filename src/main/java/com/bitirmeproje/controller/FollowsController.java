package com.bitirmeproje.controller;

import com.bitirmeproje.dto.FollowsDto;
import com.bitirmeproje.dto.PopulerKullaniciDto;
import com.bitirmeproje.model.Follows;
import com.bitirmeproje.service.FollowsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kullanici")
public class FollowsController {
    private final FollowsService followsService;
    public FollowsController(FollowsService followsService)
    {
        this.followsService=followsService;
    }
    @PostMapping("/{takip_edilen_id}/takip")
    public ResponseEntity<String>takipEt(@PathVariable("takip_edilen_id")int takipEdilenId,@RequestParam("takip_eden_id")int takipEdenId)
    {
        String sonuc=followsService.takipEt(takipEdenId,takipEdilenId);
        return ResponseEntity.ok(sonuc);
    }
    @DeleteMapping("/{takip_edilen_id}/takip")
    public ResponseEntity<String> takiptenCik(@PathVariable("takip_edilen_id") int takipEdilenId,@RequestParam("takip_eden_id") int takipEdenId)
    {
        String sonuc = followsService.takiptenCik(takipEdenId, takipEdilenId);
        return ResponseEntity.ok(sonuc);
    }
    @GetMapping("/populer")
    public ResponseEntity<List<PopulerKullaniciDto>> populerKullanicilariGetir() {
        List<PopulerKullaniciDto> populerKullanicilar = followsService.populerKullanicilariGetir();
        return ResponseEntity.ok(populerKullanicilar);
    }


}
