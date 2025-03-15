package com.bitirmeproje.repository;

import com.bitirmeproje.model.Gonderiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Gonderiler, Integer> {

    @Query("""
    SELECT g FROM Gonderiler g
    JOIN g.kullaniciId k
    JOIN Follows f ON k.kullaniciId = f.takipEdenKullaniciId.kullaniciId
    WHERE f.takipEdilenKullaniciId.kullaniciId = :kullaniciId
    ORDER BY g.gonderiTarihi DESC
    """)
    List<Gonderiler> getGonderiler(@Param("kullaniciId") int kullaniciId);
}
