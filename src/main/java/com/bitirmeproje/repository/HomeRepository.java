package com.bitirmeproje.repository;

import com.bitirmeproje.dto.home.HomeDto;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<User, Integer> {

    @Query("""
    SELECT NEW com.bitirmeproje.dto.home.HomeDto(
        g.gonderiId, g.kullaniciId.kullaniciId, g.gonderiIcerigi, 
        g.gonderiBegeniSayisi, g.gonderiTarihi
    ) 
    FROM Gonderiler g
    JOIN g.kullaniciId k
    JOIN Follows f ON k.kullaniciId = f.takipEdenKullaniciId.kullaniciId
    WHERE f.takipEdilenKullaniciId.kullaniciId = :kullaniciId
    ORDER BY g.gonderiTarihi DESC
""")
    List<HomeDto> getGonderiler(@Param("kullaniciId") int kullaniciId);


}
