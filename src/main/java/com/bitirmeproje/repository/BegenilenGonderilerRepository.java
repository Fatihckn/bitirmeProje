package com.bitirmeproje.repository;

import com.bitirmeproje.dto.begenilengonderiler.BegenilenGonderilerDto;
import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderilerAllDto;
import com.bitirmeproje.model.BegenilenGonderiler;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BegenilenGonderilerRepository extends JpaRepository<BegenilenGonderiler,Integer> {

    List<BegenilenGonderiler> findByKullaniciId(User kullanici);//Belirli bir kullanıcının beğendiği gönderileri getir

    boolean existsByGonderiIdAndKullaniciId(Gonderiler gonderiler,User kullanici);//spesifik kullanıcı spesifik gönderiyi beğendi mi

    void deleteByGonderiIdAndKullaniciId(Gonderiler gonderi,User kullanici);//beğeni kaldır

    /*@Query("SELECT b.gonderiId FROM BegenilenGonderiler b GROUP BY b.gonderiId ORDER BY COUNT(b.gonderiId) DESC")
    List<Gonderiler> findTop10ByOrderByBegeniSayisi();*/

    @Query(value = "SELECT g.gonderi_id, MAX(g.gonderi_begeni_sayisi) as gonderi_begeni_sayisi " +
            "FROM kullanicilar AS k " +
            "INNER JOIN follows AS f ON k.kullanici_id = f.takip_eden_kullanici_id " +
            "INNER JOIN gonderiler AS g ON f.takip_edilen_kullanici_id = g.kullanici_id " +
            "WHERE g.gonderi_begeni_sayisi > 0 " +
            "GROUP BY g.gonderi_id", nativeQuery = true)
    List<GonderilerAllDto> findAllBegeniler();
}
