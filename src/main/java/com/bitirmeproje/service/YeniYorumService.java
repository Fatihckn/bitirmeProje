package com.bitirmeproje.service;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.model.YeniYorumBegeniler;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.repository.UserRepository;
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


    public YeniYorumService(YeniYorumRepository yeniYorumRepository, UserRepository userRepository,
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

    private YeniYorumDto convertToDTO(YeniYorum yorum) {
        YeniYorumDto dto = new YeniYorumDto();
        dto.setKullaniciId(yorum.getKullaniciId().getKullaniciId());
        dto.setGonderiId(yorum.getGonderiId().getGonderiId());
        dto.setYorumIcerigi(yorum.getYeniYorumIcerigi());
        dto.setParentYorumId(yorum.getParentYorum() != null ? yorum.getParentYorum().getYorumId() : null);
        return dto;
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
            Optional<YeniYorum> parentYorum = getYeniYorum(yeniYorumDto.getParentYorumId());
            if (parentYorum.isPresent()) {
                yeniYorum.setParentYorum(parentYorum.get());
            } else {
                throw new CustomException(HttpStatus.NOT_FOUND, "Üst yorum bulunamadı.");
            }
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
        Optional<YeniYorum> yorum = getYeniYorum(yorumId);

        if(yorum.get().getKullaniciId().getKullaniciId() != jwtUtil.extractUserId()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yorum bulunamadi");
        }

        yeniYorumRepository.deleteById(yorumId);
    }
    public void yorumBegen(int yorumId) {
        User user = getUser();

        Optional<YeniYorum> yorum = getYeniYorum(yorumId);

        // Kullanıcının daha önce bu yorumu beğenip beğenmediğini kontrol et
        boolean begeniVar = yeniYorumBegenilerRepository.existsByYeniYorumAndKullanici(yorum.get(), user);

        if (begeniVar) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Zaten bu yorumu beğendiniz.");
        }

        // Yeni beğeni kaydı oluştur
        YeniYorumBegeniler yeniBegeni = new YeniYorumBegeniler();
        yeniBegeni.setYeniYorum(yorum.get());
        yeniBegeni.setKullanici(user);

        yeniYorumBegenilerRepository.save(yeniBegeni);

        // Yorumun beğeni sayısını artır
        YeniYorum guncellenenYorum = yorum.get();
        guncellenenYorum.setYeniYorumBegeniSayisi(guncellenenYorum.getYeniYorumBegeniSayisi() + 1);
        yeniYorumRepository.save(guncellenenYorum);
    }
    public void yorumBegeniCek(int yorumId) {
        User user = getUser();

        Optional<YeniYorum> yorum = getYeniYorum(yorumId);

        // Kullanıcının bu yoruma yaptığı beğeniyi kontrol et
        Optional<YeniYorumBegeniler> begeni = yeniYorumBegenilerRepository.findByYeniYorumAndKullanici(yorum.get(), user);

        if (begeni.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu yorumu zaten beğenmemişsiniz.");
        }

        // Beğeni kaydını sil
        yeniYorumBegenilerRepository.delete(begeni.get());

        // Yorumun beğeni sayısını azalt
        YeniYorum guncellenenYorum = yorum.get();
        guncellenenYorum.setYeniYorumBegeniSayisi(Math.max(0, guncellenenYorum.getYeniYorumBegeniSayisi() - 1));
        yeniYorumRepository.save(guncellenenYorum);
    }
    public void yorumaYanitEkle(int yorumId, YeniYorumDto yeniYorumDto) {
        User user = getUser();

        Optional<YeniYorum> parentYorum = getYeniYorum(yorumId);

        YeniYorum yeniYorum = new YeniYorum();
        yeniYorum.setKullaniciId(user);
        yeniYorum.setGonderiId(parentYorum.get().getGonderiId()); // Yanıt, aynı gönderiye ait olmalı
        yeniYorum.setYeniYorumIcerigi(yeniYorumDto.getYorumIcerigi());
        yeniYorum.setYeniYorumOlusturulmaTarihi(LocalDate.now());
        yeniYorum.setYeniYorumBegeniSayisi(0);
        yeniYorum.setParentYorum(parentYorum.get()); // Parent yorum set ediliyor

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
        Optional<YeniYorum> yeniYorum = yeniYorumRepository.findById(yorumId);
        if (yeniYorum.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yorum bulunamadı.");
        }
        return yeniYorumBegenilerRepository.countByYeniYorum(yeniYorum.get());
    }
    public List<YeniYorumDto> getBegenilenYorumlar(int kullaniciId) {
        List<YeniYorum> begenilenYorumlar = yeniYorumBegenilerRepository
                .findByKullanici_KullaniciId(kullaniciId)
                .stream()
                .map(YeniYorumBegeniler::getYeniYorum) // Beğenilen yorumları çekiyoruz
                .collect(Collectors.toList());

        return begenilenYorumlar.stream()
                .map(this::convertToDTO) // DTO'ya çeviriyoruz
                .collect(Collectors.toList());
    }

    private Optional<YeniYorum> getYeniYorum(int yorumId) {
        Optional<YeniYorum> yeniYorum = yeniYorumRepository.findById(yorumId);
        if (yeniYorum.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Yorum bulunamadi");
        }
        return yeniYorum;
    }

    private User getUser() {return findUser.findUser(jwtUtil.extractUserId());}
}