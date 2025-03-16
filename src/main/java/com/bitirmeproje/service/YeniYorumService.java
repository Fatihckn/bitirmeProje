package com.bitirmeproje.service;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConvert;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.repository.YeniYorumRepository;
import com.bitirmeproje.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class YeniYorumService {

    private final YeniYorumRepository yeniYorumRepository;
    private final GonderilerRepository gonderilerRepository;
    private final FindUser<Integer> findUser;
    private final JwtUtil jwtUtil;
    private final IEntityDtoConvert<YeniYorum,YeniYorumDto> iEntityDtoConvert;


    public YeniYorumService(YeniYorumRepository yeniYorumRepository,
                            GonderilerRepository gonderilerRepository,
                            @Qualifier("findUserById") FindUser<Integer> findUser,
                            JwtUtil jwtUtil,
                            @Qualifier("yeniYorumConverter") IEntityDtoConvert<YeniYorum, YeniYorumDto> iEntityDtoConvert) {
        this.yeniYorumRepository = yeniYorumRepository;
        this.gonderilerRepository = gonderilerRepository;
        this.findUser = findUser;
        this.jwtUtil = jwtUtil;
        this.iEntityDtoConvert = iEntityDtoConvert;
    }

    public YeniYorum yeniYorumEkle(YeniYorumDto yeniYorumDto) {
        User user = findUser.findUser(jwtUtil.extractUserId());

        Optional<Gonderiler> gonderi = gonderilerRepository.findByGonderiId(yeniYorumDto.getGonderiId());
        if (gonderi.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı.");
        }

        YeniYorum yeniYorum = new YeniYorum();
        yeniYorum.setKullaniciId(user);
        yeniYorum.setGonderiId(gonderi.get());
        yeniYorum.setYeniYorumIcerigi(yeniYorumDto.getYorumIcerigi());
        yeniYorum.setYeniYorumOlusturulmaTarihi(LocalDate.now());
        yeniYorum.setYeniYorumBegeniSayisi(0);

        // Eğer parent yorum ID varsa alt yorum olarak ata
        if (yeniYorumDto.getParentYorumId() != null) {
            YeniYorum parentYorum = getYeniYorum(yeniYorumDto.getParentYorumId());
            yeniYorum.setParentYorum(parentYorum);
        }

        return yeniYorumRepository.save(yeniYorum);
    }

    public List<YeniYorumDto> getYorumlarByGonderiId(int gonderiId) {
        List<YeniYorum> yorumlar = yeniYorumRepository.findByGonderiId_GonderiId(gonderiId);
        return yorumlar.stream().map(iEntityDtoConvert::convertToDTO).collect(Collectors.toList());
    }

    // **Yeni eklenen API**: Belirli bir kullanıcının yaptığı yorumları getir
    public List<YeniYorumDto> getYorumlarByKullaniciId(int kullaniciId) {
        List<YeniYorum> yorumlar = yeniYorumRepository.findByKullaniciId_KullaniciId(kullaniciId);

        if (yorumlar.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Yorum bulunamadi");
        }

        return yorumlar.stream().map(iEntityDtoConvert::convertToDTO).collect(Collectors.toList());
    }
    public void yorumuSil(int yorumId) {
        YeniYorum yorum = getYeniYorum(yorumId);

        if(yorum.getKullaniciId().getKullaniciId() != jwtUtil.extractUserId()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yorum bulunamadi");
        }

        yeniYorumRepository.deleteById(yorumId);
    }

    public void yorumaYanitEkle(int yorumId, YeniYorumDto yeniYorumDto) {
        User user = getUser();

        YeniYorum parentYorum = getYeniYorum(yorumId);

        YeniYorum yeniYorum = new YeniYorum();
        yeniYorum.setKullaniciId(user);
        yeniYorum.setGonderiId(parentYorum.getGonderiId()); // Yanıt, aynı gönderiye ait olmalı
        yeniYorum.setYeniYorumIcerigi(yeniYorumDto.getYorumIcerigi());
        yeniYorum.setYeniYorumOlusturulmaTarihi(LocalDate.now());
        yeniYorum.setYeniYorumBegeniSayisi(0);
        yeniYorum.setParentYorum(parentYorum); // Parent yorum set ediliyor

        yeniYorumRepository.save(yeniYorum);
    }

    public List<YeniYorumDto> getYanitlarByYorumId(int yorumId) {
        List<YeniYorum> yanitlar = yeniYorumRepository.findByParentYorum_YorumId(yorumId);

        if (yanitlar.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Yanıt Bulunamadi");
        }

        return yanitlar.stream()
                .map(iEntityDtoConvert::convertToDTO)
                .collect(Collectors.toList());
    }

    private YeniYorum getYeniYorum(int yorumId) {
        YeniYorum yeniYorum = yeniYorumRepository.findByYorumId(yorumId);
        if (yeniYorum == null) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Yorum bulunamadi");
        }
        return yeniYorum;
    }

    private User getUser() {return findUser.findUser(jwtUtil.extractUserId());}
}