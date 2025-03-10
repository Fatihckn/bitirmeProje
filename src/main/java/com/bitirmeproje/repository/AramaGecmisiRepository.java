package com.bitirmeproje.repository;

import com.bitirmeproje.dto.UserDto;
import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface AramaGecmisiRepository extends JpaRepository<AramaGecmisi, Integer>{
    List<AramaGecmisi> findByKullaniciId(User kullaniciId);//ID ye göre arama geçmişi listele
    void deleteById(int aramaGecmisiId);//ID ye göre arama geçmişi sil
    @Query("SELECT a FROM AramaGecmisi a WHERE a.kullaniciId = :kullaniciId AND a.aramaZamani BETWEEN :baslangic AND :bitis")
    List<AramaGecmisi> findByKullaniciIdAndDateRange(@Param("kullaniciId") User kullanici,
                                                     @Param("baslangic") LocalDate baslangic,
                                                     @Param("bitis") LocalDate bitis);// Kullanıcının belirli bir zaman aralığında  yaptığı aramaları listeler
    @Query("SELECT a.aramaIcerigi, COUNT(a) FROM AramaGecmisi a GROUP BY a.aramaIcerigi ORDER BY COUNT(a) DESC")
    List<Object[]> findMostPopularSearches();//En çok yapılan aramaları listeleme.

}
