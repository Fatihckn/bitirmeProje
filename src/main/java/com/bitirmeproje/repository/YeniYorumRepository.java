package com.bitirmeproje.repository;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDtoWithBegenildiMi;
import com.bitirmeproje.model.YeniYorum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YeniYorumRepository extends JpaRepository<YeniYorum, Integer> {

    // Belirli bir gönderiye ait yorumları getir
    List<YeniYorum> findByGonderiId_GonderiIdOrderByYeniYorumOlusturulmaTarihiDesc(int gonderiId);

    // Belirli bir gönderiye ait yorumları getir, aynı zamanda kullanıcı bu yorumu beğenmiş mi kontrol et
    @Query("""
    SELECT new com.bitirmeproje.dto.yeniyorum.YeniYorumDtoWithBegenildiMi(
        y.yorumId,
        y.yeniYorumIcerigi,
        y.yeniYorumOlusturulmaTarihi,
        y.yeniYorumBegeniSayisi,
        CASE WHEN yb.kullanici.kullaniciId IS NOT NULL THEN TRUE ELSE FALSE END,
        y.kullaniciId.kullaniciProfilResmi,
        y.kullaniciId.kullaniciTakmaAd
    )
    FROM YeniYorum y
    LEFT JOIN YeniYorumBegeniler yb\s
        ON y.yorumId = yb.yeniYorum.yorumId AND yb.kullanici.kullaniciId = :kullaniciId
    WHERE y.gonderiId.gonderiId = :gonderiId AND y.parentYorum IS NULL
    ORDER BY y.yeniYorumOlusturulmaTarihi ASC\s
""")
    List<YeniYorumDtoWithBegenildiMi> findByGonderiIdWithBegenildiMi(
            @Param("gonderiId") int gonderiId,
            @Param("kullaniciId") int kullaniciId
    );

    // Alt yorumları beğenildi mi ile getiren sorgu.
    @Query("""
    SELECT new com.bitirmeproje.dto.yeniyorum.YeniYorumDtoWithBegenildiMi(
        y.yorumId,
        y.yeniYorumIcerigi,
        y.yeniYorumOlusturulmaTarihi,
        y.yeniYorumBegeniSayisi,
        CASE WHEN yb.kullanici.kullaniciId IS NOT NULL THEN TRUE ELSE FALSE END,
        y.kullaniciId.kullaniciProfilResmi,
        y.kullaniciId.kullaniciTakmaAd
    )
    FROM YeniYorum y
    LEFT JOIN YeniYorumBegeniler yb\s
        ON y.yorumId = yb.yeniYorum.yorumId AND yb.kullanici.kullaniciId = :kullaniciId
    WHERE y.parentYorum.yorumId = :parentYorumId
    ORDER BY y.yeniYorumOlusturulmaTarihi ASC\s
""")
    List<YeniYorumDtoWithBegenildiMi> findAltYorumlarDtoByParentYorumId(
            @Param("parentYorumId") int parentYorumId,
            @Param("kullaniciId") int kullaniciId
    );


    // Belirli bir üst yoruma ait alt yorumları getir
    List<YeniYorum> findByParentYorum_YorumId(int parentYorumId);

    List<YeniYorum> findByKullaniciId_KullaniciId(int kullaniciId);

    YeniYorum findByYorumId(int yorumId);
}


