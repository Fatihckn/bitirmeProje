package com.bitirmeproje.repository;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.model.Gonderiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GonderilerRepository extends JpaRepository<Gonderiler, Integer> {

    // Belirli bir kullanıcının tüm gönderilerini getir
    List<Gonderiler> findByKullaniciId_KullaniciId(int kullaniciId);

    @Query("SELECT new com.bitirmeproje.dto.gonderiler.GonderiDto(" +
            "g.gonderiId, " +
            "g.gonderiIcerigi, " +
            "g.gonderiTarihi, " +
            "COALESCE(g.gonderiBegeniSayisi, 0) " + // NULL değerleri 0 yap// NULL önleme
            ") FROM Gonderiler g " +
            "LEFT JOIN g.kullaniciId u " + // Kullanıcı ilişkisinin boş olup olmadığını kontrol et
            "ORDER BY g.gonderiBegeniSayisi DESC")
    List<GonderiDto> findPopularPosts();



    // Gönderiyi ID'ye göre bul
    Optional<Gonderiler> findById(int id);

    // Belirli bir gönderiyi ID ile getir
    Gonderiler findByGonderiId(int gonderiId);

    @Modifying
    @Transactional
    @Query("UPDATE Gonderiler g SET g.gonderiBegeniSayisi = g.gonderiBegeniSayisi + 1 WHERE g.gonderiId = :gonderiId")
    void incrementBegeniSayisi(@Param("gonderiId") int gonderiId);

    @Modifying
    @Transactional
    @Query("UPDATE Gonderiler g SET g.gonderiBegeniSayisi = g.gonderiBegeniSayisi - 1 WHERE g.gonderiId = :gonderiId")
    void decreaseBegeniSayisi(@Param("gonderiId") int gonderiId);
}
