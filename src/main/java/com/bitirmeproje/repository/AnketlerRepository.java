package com.bitirmeproje.repository;

import com.bitirmeproje.dto.anketler.AnketOneriDto;
import com.bitirmeproje.dto.anketler.KullaniciCevapladigiAnketlerDto;
import com.bitirmeproje.model.Anketler;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnketlerRepository extends JpaRepository<Anketler, Integer> {

    List<Anketler> findAnketlerByKullaniciOrderByOlusturulmaTarihiDesc(User kullanici);

    @Query(value = """
    SELECT *
    FROM anketler a
    WHERE a.anket_id =:anketId
""", nativeQuery = true)
    Anketler findAnketByAnketId(int anketId);

    // Kullanıcının o ankete cevap verip vermediği bilgisi ile beraber anketi getirir.
    @Query(value = """
    SELECT new com.bitirmeproje.dto.anketler.AnketOneriDto(
    a.anketId,
    a.anketSorusu,
    a.olusturulmaTarihi,
    CASE WHEN c.cevapId IS NOT NULL THEN TRUE ELSE FALSE END \s
     )
    FROM Anketler a
    LEFT JOIN Cevaplar c ON c.anketId.anketId = a.anketId AND c.kullaniciId.kullaniciId = :kullaniciId
    WHERE a.anketId =:anketId
""")
    AnketOneriDto findAnketByAnketIdWithKullaniciCevapVerdiMi(int anketId, int kullaniciId);

    @Query("""
    SELECT new com.bitirmeproje.dto.anketler.KullaniciCevapladigiAnketlerDto(
    a.anketId,
    a.anketSorusu,
    a.olusturulmaTarihi,
    c.secenekId.secenekId
    )
    FROM Anketler a
    INNER JOIN Cevaplar c ON a.anketId = c.anketId.anketId\s
    WHERE c.kullaniciId.kullaniciId = :kullaniciId
""")
    List<KullaniciCevapladigiAnketlerDto> findKullaniciCevapladigiAnketlerDtoByKullaniciId(int kullaniciId);
}
