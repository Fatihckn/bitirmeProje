package com.bitirmeproje.service;

import com.bitirmeproje.dto.AramaGecmisiDto;
import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.AramaGecmisiRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AramaGecmisiService {
    private final AramaGecmisiRepository aramaGecmisiRepository;

    public AramaGecmisiService(AramaGecmisiRepository aramaGecmisiRepository) {
        this.aramaGecmisiRepository = aramaGecmisiRepository;
    }

    // Kullanıcının yaptığı aramayı kaydet
    public AramaGecmisi AramaKaydet(AramaGecmisi aramaGecmisi) {
        return aramaGecmisiRepository.save(aramaGecmisi);
    }

    // Kullanıcının tüm arama geçmişini getir (DTO formatında)
    public List<AramaGecmisiDto> getKullaniciAramaGecmisi(User kullanici) {
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
    public List<AramaGecmisiDto> getKullaniciAramaGecmisiByDate(User kullanici, LocalDate baslangic, LocalDate bitis) {
        return aramaGecmisiRepository.findByKullaniciIdAndDateRange(kullanici, baslangic, bitis)
                .stream()
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
        aramaGecmisiRepository.deleteById(aramaGecmisiId);
    }

    // En çok yapılan aramaları getir
    public List<Object[]> getPopulerAramalar() {
        return aramaGecmisiRepository.findMostPopularSearches();
    }
}
