package com.bitirmeproje.service;

import com.bitirmeproje.dto.FollowsDto;
import com.bitirmeproje.dto.PopulerKullaniciDto;
import com.bitirmeproje.model.Follows;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.FollowsRepository;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class FollowsService {
    private final FollowsRepository followsRepository;
    private final UserRepository userRepository;
    public FollowsService(FollowsRepository followsRepository,UserRepository userRepository)
    {
        this.followsRepository=followsRepository;
        this.userRepository=userRepository;
    }
    @Transactional
    public String takipEt(int takipEdenId,int takipEdilenId)
    {
        User takipEden=userRepository.findByKullaniciId(takipEdenId)
                .orElseThrow(()->new RuntimeException("Takip eden kullanici bulunamadı"));
        User takipEdilen=userRepository.findByKullaniciId(takipEdilenId)
                .orElseThrow(()->new RuntimeException("Takip edilen kullanici bulunamadı"));
        Optional<Follows>mevcutTakip=followsRepository.findByFollowerAndFollowing(takipEden,takipEdilen);
        if (mevcutTakip.isPresent())
        {
            return "Bu kullanici zaten takip ediyorsunuz";
        }
        Follows yeniTakip=new Follows();
        yeniTakip.setTakipEdenKullaniciId(takipEden);
        yeniTakip.setTakipEdilenKullaniciId(takipEdilen);
        yeniTakip.setTakipEtmeTarihi(LocalDate.now());
        followsRepository.save(yeniTakip);
        return "Takip etme başarılı";
    }
    @Transactional
    public String takiptenCik(int takipEdenId, int takipEdilenId) {
        Optional<Follows> takip = followsRepository.findByFollowerAndFollowing(
                new User(takipEdenId), new User(takipEdilenId));

        if (takip.isPresent()) {
            followsRepository.takiptenCik(takipEdenId, takipEdilenId);
            return "Takipten çıkıldı!";
        } else {
            return "Bu kullanıcıyı zaten takip etmiyorsunuz.";
        }
    }
    public List<PopulerKullaniciDto> populerKullanicilariGetir() {
        return followsRepository.findMostFollowedUsers();
    }

}
