package com.bitirmeproje.repository;

import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AramaGecmisiRepository extends JpaRepository<AramaGecmisi, Integer>{

    List<AramaGecmisi> findByKullaniciId(User kullaniciId);//ID ye göre arama geçmişi listele

    void deleteById(int aramaGecmisiId);//ID ye göre arama geçmişi sil

    AramaGecmisi findAramaGecmisiByAramaGecmisiId(int id);
}
