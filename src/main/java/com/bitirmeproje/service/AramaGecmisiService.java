package com.bitirmeproje.service;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.AramaGecmisiRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AramaGecmisiService {
    private final AramaGecmisiRepository aramaGecmisiRepository;
    private final FindUser<Integer> findUserById; // ID ile kullanıcı bul
    private final FindUser<String> findUserByEmail; // Email ile kullanıcı bul

    public AramaGecmisiService(AramaGecmisiRepository aramaGecmisiRepository,
                               @Qualifier("findUserById") FindUser<Integer> findUserById,
                               @Qualifier("findUserByEmail")  FindUser<String> findUserByEmail) {
        this.aramaGecmisiRepository = aramaGecmisiRepository;
        this.findUserById = findUserById;
        this.findUserByEmail = findUserByEmail;
    }

    // Kullanıcının yaptığı aramayı kaydet
    public void AramaKaydet(AramaGecmisiDto aramaGecmisiDto, String kullaniciEmail) {
        // Kullanıcıyı veritabanından çekiyoruz
        User kullanici = findUserByEmail.findUser(kullaniciEmail);

        // DTO'yu Entity'ye çeviriyoruz
        AramaGecmisi yeniArama = new AramaGecmisi();
        yeniArama.setAramaIcerigi(aramaGecmisiDto.getAramaIcerigi());
        yeniArama.setAramaZamani(LocalDate.now()); // Arama zamanını sistem zamanı olarak al
        yeniArama.setKullaniciId(kullanici); // ManyToOne ilişkisini set ettik

        aramaGecmisiRepository.save(yeniArama);
    }

    // Kullanıcının tüm arama geçmişini getir (DTO formatında)
    public List<AramaGecmisiDto> getKullaniciAramaGecmisi(int kullaniciId) {

        User kullanici = findUserById.findUser(kullaniciId);

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
    public List<AramaGecmisiDto> getKullaniciAramaGecmisiByDate(int kullaniciId, LocalDate baslangic, LocalDate bitis) {

        User kullanici = findUserById.findUser(kullaniciId);

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
    public void deleteArama(int aramaGecmisiId, String kullaniciEmail) {
        // 1️⃣ Kullanıcıyı JWT token içindeki email'den bul
        User kullanici = findUserByEmail.findUser(kullaniciEmail);

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
