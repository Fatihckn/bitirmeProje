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
    @Query("""
    SELECT new com.bitirmeproje.dto.gonderiler.GonderiDto(
        g.gonderiId,
        g.gonderiIcerigi,
        g.gonderiTarihi,
        COALESCE(g.gonderiBegeniSayisi, 0),
        g.kullaniciId.kullaniciTakmaAd,
        g.gonderiMedyaUrl,
        CASE WHEN bg IS NOT NULL THEN TRUE ELSE FALSE END,
        COUNT(y.yorumId)   \s
    )
    FROM Gonderiler g
    LEFT JOIN BegenilenGonderiler bg\s
        ON g.gonderiId = bg.gonderiId.gonderiId AND bg.kullaniciId.kullaniciId = :kullaniciId
    LEFT JOIN YeniYorum y
        ON y.gonderiId.gonderiId = g.gonderiId   \s
    WHERE g.kullaniciId.kullaniciId = :kullaniciId
    GROUP BY g.gonderiId, g.gonderiIcerigi, g.gonderiTarihi,
         g.gonderiBegeniSayisi, g.kullaniciId.kullaniciTakmaAd,
         g.gonderiMedyaUrl, bg   \s
    ORDER BY g.gonderiTarihi DESC
   \s""")
    List<GonderiDto> findByKullaniciId_KullaniciIdOrderByGonderiTarihiDesc(int kullaniciId);

    @Query("""
                SELECT new com.bitirmeproje.dto.gonderiler.GonderiDto(
                    g.gonderiId,
                    g.gonderiIcerigi,
                    g.gonderiTarihi,
                    COALESCE(g.gonderiBegeniSayisi, 0),
                    g.kullaniciId.kullaniciTakmaAd,
                    g.gonderiMedyaUrl,
                    CASE WHEN bg IS NOT NULL THEN TRUE ELSE FALSE END,
                    COUNT(y.yorumId)
                )
                FROM Gonderiler g
                LEFT JOIN BegenilenGonderiler bg\s
                    ON g.gonderiId = bg.gonderiId.gonderiId\s
                    AND bg.kullaniciId.kullaniciId = :girenKullanici
                LEFT JOIN YeniYorum y
                    ON y.gonderiId.gonderiId = g.gonderiId   \s
                WHERE g.kullaniciId.kullaniciId = :girilenKullanici
                GROUP BY g.gonderiId, g.gonderiIcerigi, g.gonderiTarihi,
                             g.gonderiBegeniSayisi, g.kullaniciId.kullaniciTakmaAd,
                             g.gonderiMedyaUrl, bg          \s
                ORDER BY g.gonderiTarihi DESC
           \s""")
    List<GonderiDto> findProfilGonderileriWithBegeniDurumu(
            @Param("girilenKullanici") int girilenKullanici,
            @Param("girenKullanici") int girenKullanici
    );

    @Query("SELECT new com.bitirmeproje.dto.gonderiler.GonderiDto(" +
            "g.gonderiId, " +
            "g.gonderiIcerigi, " +
            "g.gonderiTarihi, " +
            "COALESCE(g.gonderiBegeniSayisi, 0), " + // NULL değerleri 0 yap// NULL önleme
            "g.kullaniciId.kullaniciTakmaAd" +
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

    @Query("""
    SELECT new com.bitirmeproje.dto.gonderiler.GonderiDto(
        g.gonderiId,
        g.gonderiIcerigi,
        g.gonderiTarihi,
        COALESCE(g.gonderiBegeniSayisi, 0),
        g.kullaniciId.kullaniciTakmaAd,
        g.gonderiMedyaUrl,
        CASE WHEN bg IS NOT NULL THEN TRUE ELSE FALSE END
    )
    FROM Gonderiler g
    LEFT JOIN BegenilenGonderiler bg\s
        ON g.gonderiId = bg.gonderiId.gonderiId\s
        AND bg.kullaniciId.kullaniciId = :girenKullanici
    WHERE g.gonderiId = :gonderiId
""")
    GonderiDto findByGonderiIdWithBegenildiMi(@Param("gonderiId") int gonderiId,
                                              @Param("girenKullanici") int kullaniciId);
}
