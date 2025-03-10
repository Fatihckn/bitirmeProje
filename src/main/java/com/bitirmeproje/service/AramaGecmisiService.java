package com.bitirmeproje.service;

import com.bitirmeproje.dto.AramaGecmisiDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.UserIdControl;
import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.AramaGecmisiRepository;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AramaGecmisiService {
    private final AramaGecmisiRepository aramaGecmisiRepository;
    private final UserRepository userRepository;
    private final UserIdControl userIdControl;

    public AramaGecmisiService(AramaGecmisiRepository aramaGecmisiRepository, UserRepository userRepository, UserIdControl userIdControl) {
        this.aramaGecmisiRepository = aramaGecmisiRepository;
        this.userRepository = userRepository;
        this.userIdControl = userIdControl;
    }

    // Kullanıcının yaptığı aramayı kaydet
    public AramaGecmisi AramaKaydet(AramaGecmisiDto aramaGecmisiDto) {
        // Kullanıcıyı veritabanından çekiyoruz
        User kullanici = userRepository.findById(aramaGecmisiDto.getKullaniciId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"Kullanıcı Bulunamadı"));

        // DTO'yu Entity'ye çeviriyoruz
        AramaGecmisi aramaGecmisi = new AramaGecmisi();
        aramaGecmisi.setAramaIcerigi(aramaGecmisiDto.getAramaIcerigi());
        aramaGecmisi.setAramaZamani(aramaGecmisiDto.getAramaZamani());
        aramaGecmisi.setKullaniciId(kullanici); // ManyToOne ilişkisini set ettik

        // Veritabanına kaydet ve döndür
        return aramaGecmisiRepository.save(aramaGecmisi);
    }

    // Kullanıcının tüm arama geçmişini getir (DTO formatında)
    public List<AramaGecmisiDto> getKullaniciAramaGecmisi(int kullaniciId) {

        User kullanici = userIdControl.findById(kullaniciId);

        return aramaGecmisiRepository.findByKullaniciId(kullanici)
                .stream()
                .map(arama -> new AramaGecmisiDto(
                        arama.getAramaGecmisiId(), // Arama geçmişi ID
                        arama.getAramaIcerigi(), // Arama içeriği
                        arama.getAramaZamani(), // Arama zamanı
                        arama.getKullaniciId().getKullaniciId() // Kullanıcı ID
                ))
                .collect(Collectors.toList());
    }

    // Kullanıcının belirli tarih aralığındaki aramalarını getir
    public List<AramaGecmisiDto> getKullaniciAramaGecmisiByDate(int kullaniciId, LocalDate baslangic, LocalDate bitis) {

        User kullanici = userIdControl.findById(kullaniciId);

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
                        arama.getAramaZamani(),
                        arama.getKullaniciId().getKullaniciId()
                ))
                .collect(Collectors.toList());
    }

    // Arama geçmişinden belirli bir kaydı sil
    public void deleteArama(int aramaGecmisiId) {

        AramaGecmisi aramaGecmisi = aramaGecmisiRepository.findById(aramaGecmisiId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Arama geçmişi bulunamadı!"));

        aramaGecmisiRepository.deleteById(aramaGecmisiId);
    }

    // En çok yapılan aramaları getir
    public List<Object[]> getPopulerAramalar() {
        return aramaGecmisiRepository.findMostPopularSearches();
    }
}
