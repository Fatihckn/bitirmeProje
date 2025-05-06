package com.bitirmeproje.service.gonderiler;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderiEkleDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.MedyaTuru;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.service.r2storage.R2StorageService;
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

    private final static String r2PublicBaseUrl = "https://media.bitirmeproje.xyz";

    public GonderilerService(GonderilerRepository gonderilerRepository, GetUserByToken getUserByToken,
                             R2StorageService r2StorageService) {
        this.gonderilerRepository = gonderilerRepository;
        this.getUserByToken = getUserByToken;
        this.r2StorageService = r2StorageService;
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

    public GonderiDto getArananGonderi(int gonderiId) {
        User user = getUserByToken.getUser();

        GonderiDto gonderi = gonderilerRepository.findByGonderiIdWithBegenildiMi(gonderiId, user.getKullaniciId());

        if(gonderi == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Gonderi Bulunamadi");
        }

        gonderi.setKullaniciFoto(user.getKullaniciProfilResmi());
        return gonderi;
    }

    private Gonderiler findGonderi(int gonderiId) {
        return gonderilerRepository.findById(gonderiId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı!"));
    }
}
