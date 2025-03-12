package com.bitirmeproje.service;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.model.YeniYorumBegeniler;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.repository.YeniYorumBegenilerRepository;
import com.bitirmeproje.repository.YeniYorumRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class YeniYorumService {

    private final YeniYorumRepository yeniYorumRepository;
    private final UserRepository userRepository;
    private final GonderilerRepository gonderilerRepository;
    private final YeniYorumBegenilerRepository yeniYorumBegenilerRepository;


    public YeniYorumService(YeniYorumRepository yeniYorumRepository, UserRepository userRepository, GonderilerRepository gonderilerRepository,YeniYorumBegenilerRepository yeniYorumBegenilerRepository) {
        this.yeniYorumRepository = yeniYorumRepository;
        this.userRepository = userRepository;
        this.gonderilerRepository = gonderilerRepository;
        this.yeniYorumBegenilerRepository=yeniYorumBegenilerRepository;
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
        Optional<User> user = userRepository.findByKullaniciId(yeniYorumDto.getKullaniciId());
        if (user.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı.");
        }

        Optional<Gonderiler> gonderi = gonderilerRepository.findByGonderiId(yeniYorumDto.getGonderiId());
        if (gonderi.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı.");
        }

        YeniYorum yeniYorum = new YeniYorum();
        yeniYorum.setKullaniciId(user.get());
        yeniYorum.setGonderiId(gonderi.get());
        yeniYorum.setYeniYorumIcerigi(yeniYorumDto.getYorumIcerigi());
        yeniYorum.setYeniYorumOlusturulmaTarihi(LocalDate.now());
        yeniYorum.setYeniYorumBegeniSayisi(0);

        // Eğer parent yorum ID varsa alt yorum olarak ata
        if (yeniYorumDto.getParentYorumId() != null) {
            Optional<YeniYorum> parentYorum = yeniYorumRepository.findById(yeniYorumDto.getParentYorumId());
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
        return yorumlar.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    public void yorumuSil(int yorumId) {
        Optional<YeniYorum> yorum = yeniYorumRepository.findById(yorumId);
        if (yorum.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yorum bulunamadı.");
        }
        yeniYorumRepository.deleteById(yorumId);
    }
    public void yorumBegen(int yorumId, int kullaniciId) {
        Optional<User> user = userRepository.findById(kullaniciId);
        if (user.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı.");
        }

        Optional<YeniYorum> yorum = yeniYorumRepository.findById(yorumId);
        if (yorum.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yorum bulunamadı.");
        }

        // Kullanıcının daha önce bu yorumu beğenip beğenmediğini kontrol et
        boolean begeniVar = yeniYorumBegenilerRepository.existsByYeniYorumAndKullanici(yorum.get(), user.get());

        if (begeniVar) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Zaten bu yorumu beğendiniz.");
        }

        // Yeni beğeni kaydı oluştur
        YeniYorumBegeniler yeniBegeni = new YeniYorumBegeniler();
        yeniBegeni.setYeniYorum(yorum.get());
        yeniBegeni.setKullanici(user.get());

        yeniYorumBegenilerRepository.save(yeniBegeni);

        // Yorumun beğeni sayısını artır
        YeniYorum guncellenenYorum = yorum.get();
        guncellenenYorum.setYeniYorumBegeniSayisi(guncellenenYorum.getYeniYorumBegeniSayisi() + 1);
        yeniYorumRepository.save(guncellenenYorum);
    }
    public void yorumBegeniCek(int yorumId, int kullaniciId) {
        Optional<User> user = userRepository.findById(kullaniciId);
        if (user.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı.");
        }

        Optional<YeniYorum> yorum = yeniYorumRepository.findById(yorumId);
        if (yorum.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yorum bulunamadı.");
        }

        // Kullanıcının bu yoruma yaptığı beğeniyi kontrol et
        Optional<YeniYorumBegeniler> begeni = yeniYorumBegenilerRepository.findByYeniYorumAndKullanici(yorum.get(), user.get());

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
    public YeniYorum yorumaYanitEkle(int parentYorumId, YeniYorumDto yeniYorumDto) {
        Optional<User> user = userRepository.findById(yeniYorumDto.getKullaniciId());
        if (user.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı.");
        }

        Optional<YeniYorum> parentYorum = yeniYorumRepository.findById(parentYorumId);
        if (parentYorum.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yanıt eklemek istediğiniz yorum bulunamadı.");
        }

        YeniYorum yeniYorum = new YeniYorum();
        yeniYorum.setKullaniciId(user.get());
        yeniYorum.setGonderiId(parentYorum.get().getGonderiId()); // Yanıt, aynı gönderiye ait olmalı
        yeniYorum.setYeniYorumIcerigi(yeniYorumDto.getYorumIcerigi());
        yeniYorum.setYeniYorumOlusturulmaTarihi(LocalDate.now());
        yeniYorum.setYeniYorumBegeniSayisi(0);
        yeniYorum.setParentYorum(parentYorum.get()); // Parent yorum set ediliyor

        return yeniYorumRepository.save(yeniYorum);
    }
    public List<YeniYorumDto> getYanıtlarByYorumId(int yorumId) {
        List<YeniYorum> yanitlar = yeniYorumRepository.findByParentYorum_YorumId(yorumId);
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
}