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
        yeniArama.setAramaIcerigi(aramaGecmisiDto.getAramaIcerigi());
        yeniArama.setAramaZamani(LocalDate.now()); // Arama zamanını sistem zamanı olarak al
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

        // Eğer arama geçmişi bulunamazsa hata fırlat
        if (aramaGecmisiList.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Belirtilen tarih aralığında arama geçmişi bulunamadı!");
        }

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
        // 1️⃣ Kullanıcıyı JWT token içindeki email'den bul
        User kullanici = getUserByToken.getUser();

        // 2️⃣ Arama geçmişini veritabanından çek
        AramaGecmisi aramaGecmisi = aramaGecmisiRepository.findById(aramaGecmisiId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Arama geçmişi bulunamadı!"));

        // 3️⃣ Silme yetkisi var mı kontrol et (Arama geçmişi bu kullanıcıya mı ait?)
        if (!aramaGecmisi.getKullaniciId().equals(kullanici)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Arama geçmişi Bulunamadı!");
        }

        // 4️⃣ Arama geçmişini sil
        aramaGecmisiRepository.deleteById(aramaGecmisiId);
    }

    // En çok yapılan aramaları getir
    public List<Object[]> getPopulerAramalar() {
        return aramaGecmisiRepository.findMostPopularSearches();
    }
}
