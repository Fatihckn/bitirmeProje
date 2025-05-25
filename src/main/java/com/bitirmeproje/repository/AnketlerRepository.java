package com.bitirmeproje.repository;

import com.bitirmeproje.dto.anketler.AnketOneriDto;
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
    LEFT JOIN Cevaplar c ON c.anketId.anketId = a.anketId
    WHERE a.anketId =:anketId
""")
    AnketOneriDto findAnketByAnketIdWithKullaniciCevapVerdiMi(int anketId);
}
