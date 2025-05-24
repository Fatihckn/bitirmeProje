package com.bitirmeproje.repository;

import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AramaGecmisiRepository extends JpaRepository<AramaGecmisi, Integer>{

    List<AramaGecmisi> findByKullaniciIdOrderByAramaZamaniDesc(User kullaniciId);//ID ye göre arama geçmişi listele

    void deleteById(int aramaGecmisiId);//ID ye göre arama geçmişi sil

    void deleteByKullaniciId(User kullaniciId);

    AramaGecmisi findAramaGecmisiByAramaGecmisiId(int id);

    @Query(value = "SELECT COUNT(*)" +
            " FROM arama_gecmisi" +
            " WHERE aranan_kullanici_id = :aranan_kullanici_id AND kullanici_id = :arayan_kullanici_id ", nativeQuery = true)
    int arananKullaniciIdVarMi(@Param("aranan_kullanici_id") int kullaniciId,@Param("arayan_kullanici_id") int girisYapanKullaniciId);
}
