package com.bitirmeproje.service.aramagecmisi;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.AramaGecmisiRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AramaGecmisiService implements IAramaGecmisiService {
    private final AramaGecmisiRepository aramaGecmisiRepository;
    private final GetUserByToken getUserByToken;

    public AramaGecmisiService(AramaGecmisiRepository aramaGecmisiRepository,
                               GetUserByToken getUserByToken) {
        this.aramaGecmisiRepository = aramaGecmisiRepository;
        this.getUserByToken = getUserByToken;
    }

    // Kullanıcının yaptığı aramayı kaydet
    public void AramaKaydet(AramaGecmisiDto aramaGecmisiDto) {
        // Kullanıcıyı veritabanından çekiyoruz
        User kullanici = getUserByToken.getUser();

        // DTO'yu Entity'ye çeviriyoruz
        AramaGecmisi yeniArama = new AramaGecmisi();
        yeniArama.setAramaIcerigi(aramaGecmisiDto.aramaIcerigi());
        yeniArama.setAramaZamani(LocalDateTime.now()); // Arama zamanını sistem zamanı olarak al
        yeniArama.setKullaniciId(kullanici); // ManyToOne ilişkisini set ettik

        aramaGecmisiRepository.save(yeniArama);
    }

    // Kullanıcının tüm arama geçmişini getir (DTO formatında)
    public List<AramaGecmisiDto> getKullaniciAramaGecmisi() {

        User kullanici = getUserByToken.getUser();

        return aramaGecmisiRepository.findByKullaniciId(kullanici)
                .stream()
                .map(arama -> new AramaGecmisiDto(
                        arama.getAramaGecmisiId(), // Arama geçmişi ID
                        arama.getAramaIcerigi(), // Arama içeriği
                        arama.getAramaZamani() // Arama zamanı
                ))
                .collect(Collectors.toList());
    }

    // Kullanıcının belirli tarih aralığındaki aramalarını getir
    public List<AramaGecmisiDto> getKullaniciAramaGecmisiByDate(LocalDate baslangic, LocalDate bitis) {

        User kullanici = getUserByToken.getUser();

        // Tarih kontrolü: Başlangıç tarihi, bitiş tarihinden sonra olamaz
        if (baslangic.isAfter(bitis)) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Başlangıç tarihi, bitiş tarihinden sonra olamaz!");
        }

        // Veritabanından tarih aralığına göre arama geçmişini çek
        List<AramaGecmisi> aramaGecmisiList = aramaGecmisiRepository.findByKullaniciIdAndDateRange(kullanici, baslangic, bitis);

        // DTO'ya dönüştür ve döndür
        return aramaGecmisiList.stream()
                .map(arama -> new AramaGecmisiDto(
                        arama.getAramaGecmisiId(),
                        arama.getAramaIcerigi(),
                        arama.getAramaZamani()
                ))
                .collect(Collectors.toList());
    }

    // Arama geçmişinden belirli bir kaydı sil
    public void deleteArama(int aramaGecmisiId) {
        // Kullanıcıyı JWT token içindeki email'den bul
        User kullanici = getUserByToken.getUser();

        // Arama geçmişini veritabanından çek
        AramaGecmisi aramaGecmisi = aramaGecmisiRepository.findAramaGecmisiByAramaGecmisiId(aramaGecmisiId);

        // Silme yetkisi var mı kontrol et (Arama geçmişi bu kullanıcıya mı ait?)!!! Bu kısımlar değiştirilebilir şuanlık kontrol kalsın.
        if (!aramaGecmisi.getKullaniciId().equals(kullanici)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Bu gönderiyi silme yetkiniz yok.");
        }

        // Arama geçmişini sil
        aramaGecmisiRepository.deleteById(aramaGecmisiId);
    }

    // En çok yapılan aramaları getir
    public List<Object[]> getPopulerAramalar() {
        return aramaGecmisiRepository.findMostPopularSearches();
    }
}
