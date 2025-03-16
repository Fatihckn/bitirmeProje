package com.bitirmeproje.service;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.model.YeniYorumBegeniler;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.repository.YeniYorumBegenilerRepository;
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
    private final YeniYorumBegenilerRepository yeniYorumBegenilerRepository;
    private final FindUser<Integer> findUser;
    private final JwtUtil jwtUtil;


    public YeniYorumService(YeniYorumRepository yeniYorumRepository,
                            GonderilerRepository gonderilerRepository,
                            YeniYorumBegenilerRepository yeniYorumBegenilerRepository,
                            @Qualifier("findUserById") FindUser<Integer> findUser,
                            JwtUtil jwtUtil) {
        this.yeniYorumRepository = yeniYorumRepository;
        this.gonderilerRepository = gonderilerRepository;
        this.yeniYorumBegenilerRepository=yeniYorumBegenilerRepository;
        this.findUser = findUser;
        this.jwtUtil = jwtUtil;
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
        return yorumlar.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // **Yeni eklenen API**: Belirli bir kullanıcının yaptığı yorumları getir
    public List<YeniYorumDto> getYorumlarByKullaniciId(int kullaniciId) {
        List<YeniYorum> yorumlar = yeniYorumRepository.findByKullaniciId_KullaniciId(kullaniciId);

        if (yorumlar.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Yorum bulunamadi");
        }

        return yorumlar.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public void yorumuSil(int yorumId) {
        YeniYorum yorum = getYeniYorum(yorumId);

        if(yorum.getKullaniciId().getKullaniciId() != jwtUtil.extractUserId()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yorum bulunamadi");
        }

        yeniYorumRepository.deleteById(yorumId);
    }
    public void yorumBegen(int yorumId) {
        User user = getUser();

        YeniYorum yorum = getYeniYorum(yorumId);

        // Kullanıcının daha önce bu yorumu beğenip beğenmediğini kontrol et
        boolean begeniVar = yeniYorumBegenilerRepository.existsByYeniYorumAndKullanici(yorum, user);

        if (begeniVar) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Zaten bu yorumu beğendiniz.");
        }

        // Yeni beğeni kaydı oluştur
        YeniYorumBegeniler yeniBegeni = new YeniYorumBegeniler();
        yeniBegeni.setYeniYorum(yorum);
        yeniBegeni.setKullanici(user);

        yeniYorumBegenilerRepository.save(yeniBegeni);

        // Yorumun beğeni sayısını artır
        yorum.setYeniYorumBegeniSayisi(yorum.getYeniYorumBegeniSayisi() + 1);
        yeniYorumRepository.save(yorum);
    }
    public void yorumBegeniCek(int yorumId) {
        User user = getUser();

        YeniYorum yorum = getYeniYorum(yorumId);

        // Kullanıcının bu yoruma yaptığı beğeniyi kontrol et
        Optional<YeniYorumBegeniler> begeni = yeniYorumBegenilerRepository.findByYeniYorumAndKullanici(yorum, user);

        if (begeni.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu yorumu zaten beğenmemişsiniz.");
        }

        // Beğeni kaydını sil
        yeniYorumBegenilerRepository.delete(begeni.get());

        // Yorumun beğeni sayısını azalt
        yorum.setYeniYorumBegeniSayisi(Math.max(0, yorum.getYeniYorumBegeniSayisi() - 1));
        yeniYorumRepository.save(yorum);
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
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public int getBegeniSayisi(int yorumId) {
        YeniYorum yeniYorum = getYeniYorum(yorumId);

        int begeniSayisi = yeniYorumBegenilerRepository.countByYeniYorum(yeniYorum);

        if (begeniSayisi == 0) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Yorumun begeni sayisi yoktur");
        }
        return begeniSayisi;
    }

    public List<YeniYorumDto> getBegenilenYorumlar(int kullaniciId) {
        List<YeniYorum> begenilenYorumlar = yeniYorumBegenilerRepository
                .findByKullanici_KullaniciId(kullaniciId)
                .stream()
                .map(YeniYorumBegeniler::getYeniYorum) // Beğenilen yorumları çekiyoruz
                .toList();

        return begenilenYorumlar.stream()
                .map(this::convertToDTO) // DTO'ya çeviriyoruz
                .collect(Collectors.toList());
    }

    private YeniYorumDto convertToDTO(YeniYorum yorum) {
        YeniYorumDto dto = new YeniYorumDto();
        dto.setYeniYorumId(yorum.getYorumId());
        dto.setKullaniciId(yorum.getKullaniciId().getKullaniciId());
        dto.setGonderiId(yorum.getGonderiId().getGonderiId());
        dto.setYorumIcerigi(yorum.getYeniYorumIcerigi());
        dto.setParentYorumId(yorum.getParentYorum() != null ? yorum.getParentYorum().getYorumId() : null);
        return dto;
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