package com.bitirmeproje.service.gonderiler;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderiEkleDto;
import com.bitirmeproje.dto.gonderiler.GonderiYorumlarDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.MedyaTuru;
import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.repository.YeniYorumRepository;
import com.bitirmeproje.service.r2storage.R2StorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GonderilerService implements IGonderilerService {

    private final GonderilerRepository gonderilerRepository;
    private final GetUserByToken getUserByToken;
    private final R2StorageService r2StorageService;
    private final FindUser<String> findUser;
    private final YeniYorumRepository yeniYorumRepository;

    private final static String r2PublicBaseUrl = "https://media.bitirmeproje.xyz";

    public GonderilerService(GonderilerRepository gonderilerRepository, GetUserByToken getUserByToken,
                             R2StorageService r2StorageService,@Qualifier("findUserByTakmaAd") FindUser<String> findUser,
                             YeniYorumRepository yeniYorumRepository) {
        this.gonderilerRepository = gonderilerRepository;
        this.getUserByToken = getUserByToken;
        this.r2StorageService = r2StorageService;
        this.findUser = findUser;
        this.yeniYorumRepository = yeniYorumRepository;
    }

    // Belirli bir kullanıcının tüm gönderilerini getir
    public List<GonderiDto> kullaniciGonderileriniGetir() {
        User user = getUserByToken.getUser();

        return gonderilerRepository.findByKullaniciId_KullaniciIdOrderByGonderiTarihiDesc(user.getKullaniciId());
    }

    // Yeni gönderi ekle
    public void yeniGonderiEkle(GonderiEkleDto gonderiEkleDto) {
        MultipartFile medyaDosyasi = gonderiEkleDto.getGonderiMedyaUrl();
        String dosyaYolu = null;

        if (medyaDosyasi != null && !medyaDosyasi.isEmpty()) {
            // Dosya adı ve uzantısını al
            String dosyaAdi = medyaDosyasi.getOriginalFilename();
            String dosyaUzantisi = Optional.ofNullable(dosyaAdi)
                    .filter(name -> name.contains("."))
                    .map(name -> name.substring(name.lastIndexOf(".")))
                    .orElse(".bin");

            // Özel dosya ismi üret
            String fileName = "medyalar/user_" + LocalDateTime.now() + "_" + UUID.randomUUID() + dosyaUzantisi;

            // Sadece medya varsa R2'ye yükle
            dosyaYolu = r2StorageService.uploadFile(medyaDosyasi, fileName);
        }

        // DTO'dan Entity'e dönüşüm
        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiIcerigi(gonderiEkleDto.getGonderiIcerigi());
        gonderi.setGonderiBegeniSayisi(0);
        gonderi.setGonderiTarihi(LocalDateTime.now());
        gonderi.setKullaniciId(getUserByToken.getUser());
        String turStr = gonderiEkleDto.getGonderiMedyaTuru();
        try{
            if (turStr != null) {
                gonderi.setGonderiMedyaTuru(MedyaTuru.valueOf(turStr.toUpperCase()));
            }
        }catch (IllegalArgumentException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Geçersiz medya türü: " + turStr);
        }

        // Medya varsa URL'yi set et
        if (dosyaYolu != null) {
            gonderi.setGonderiMedyaUrl(r2PublicBaseUrl + "/" + dosyaYolu);
        }

        // Gönderiyi kaydet
        gonderilerRepository.save(gonderi);
    }


    // En popüler gönderileri getir
    public List<GonderiDto> populerGonderileriGetir() {
        return gonderilerRepository.findPopularPosts();
    }

    // Gönderiyi sil
    public void gonderiSil(int gonderiId) {
        Gonderiler gonderi = findGonderi(gonderiId);

        if(gonderi.getKullaniciId().getKullaniciId() != (getUserByToken.getUser().getKullaniciId())) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Bu gönderiyi silme yetkiniz yok.");
        }

        String fullUrl = gonderi.getGonderiMedyaUrl();

        if (fullUrl != null) {
            String dosyaAdi = fullUrl.substring(fullUrl.indexOf("medyalar/"));
            r2StorageService.deleteFile(dosyaAdi);
        }

        gonderilerRepository.deleteById(gonderi.getGonderiId());
    }

    public GonderiYorumlarDto getArananGonderi(int gonderiId) {
        User user = getUserByToken.getUser();

        GonderiDto gonderi = gonderilerRepository.findByGonderiIdWithBegenildiMi(gonderiId, user.getKullaniciId());

        if(gonderi == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Gonderi Bulunamadi");
        }

        GonderiYorumlarDto gonderiYorumlarDto = new GonderiYorumlarDto();
        gonderiYorumlarDto.setGonderiId(gonderiId);
        gonderiYorumlarDto.setGonderiIcerigi(gonderi.getGonderiIcerigi());
        gonderiYorumlarDto.setGonderiTarihi(gonderi.getGonderiTarihi());
        gonderiYorumlarDto.setGonderiMedyaUrl(gonderi.getGonderiMedyaUrl());
        gonderiYorumlarDto.setBegenildiMi(gonderi.getBegenildiMi());
        gonderiYorumlarDto.setKullaniciTakmaAd(gonderi.getKullaniciTakmaAd());
        gonderiYorumlarDto.setGonderiBegeniSayisi(gonderi.getGonderiBegeniSayisi());

        List<YeniYorum> yorumlar = yeniYorumRepository.findByGonderiId_GonderiIdOrderByYeniYorumOlusturulmaTarihiDesc(gonderiId);
        gonderiYorumlarDto.setYorumlar(yorumlar);

        // Login olan kişi
        gonderiYorumlarDto.setKullaniciFoto(user.getKullaniciProfilResmi());

        // Gonderiyi atan kişi
        findUser.findUser(gonderi.getKullaniciTakmaAd());
        User gonderiAtan = findUser.findUser(gonderi.getKullaniciTakmaAd());
        gonderiYorumlarDto.setGonderiAtanKullaniciFoto(gonderiAtan.getKullaniciProfilResmi());

        return gonderiYorumlarDto;
    }

    private Gonderiler findGonderi(int gonderiId) {
        return gonderilerRepository.findById(gonderiId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı!"));
    }
}
