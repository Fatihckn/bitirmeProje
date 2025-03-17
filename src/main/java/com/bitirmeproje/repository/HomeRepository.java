package com.bitirmeproje.repository;

import com.bitirmeproje.dto.home.HomeDto;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<User, Integer> {

//    @Query("""
//    SELECT NEW com.bitirmeproje.dto.home.HomeDto(
//        g.gonderiId, g.kullaniciId.kullaniciId, g.gonderiIcerigi,g.gonderiBegeniSayisi, g.gonderiTarihi, f.takipEdilenKullaniciId.kullaniciTakmaAd
//    )
//    FROM User k
//    INNER JOIN Follows f ON k.kullaniciId = f.takipEdenKullaniciId.kullaniciId
//    INNER JOIN Gonderiler g ON f.takipEdilenKullaniciId = g.kullaniciId
//    WHERE k.kullaniciId = :kullaniciId
//    ORDER BY g.gonderiTarihi DESC
//""")
//    List<HomeDto> getGonderiler(@Param("kullaniciId") int kullaniciId);


    @Query("""
    SELECT NEW com.bitirmeproje.dto.home.HomeDto(
        g.gonderiId, g.kullaniciId.kullaniciId, g.gonderiIcerigi, g.gonderiBegeniSayisi, g.gonderiTarihi,
        f.takipEdilenKullaniciId.kullaniciTakmaAd,
        CASE WHEN bg.gonderiId IS NOT NULL THEN true ELSE false END
    )
    FROM User k
    INNER JOIN Follows f ON k.kullaniciId = f.takipEdenKullaniciId.kullaniciId
    INNER JOIN Gonderiler g ON f.takipEdilenKullaniciId = g.kullaniciId
    LEFT JOIN BegenilenGonderiler bg ON g.gonderiId = bg.gonderiId.gonderiId AND bg.kullaniciId.kullaniciId = :kullaniciId
    WHERE k.kullaniciId = :kullaniciId
    ORDER BY g.gonderiTarihi DESC
""")
    List<HomeDto> getGonderiler(@Param("kullaniciId") int kullaniciId);

}
