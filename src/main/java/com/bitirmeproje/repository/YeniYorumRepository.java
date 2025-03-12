package com.bitirmeproje.repository;

import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.YeniYorum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YeniYorumRepository extends JpaRepository<YeniYorum, Integer> {

    // Belirli bir gönderiye ait yorumları getir
    List<YeniYorum> findByGonderiId_GonderiId(int gonderiId);


    // Belirli bir üst yoruma ait alt yorumları getir
    List<YeniYorum> findByParentYorum_YorumId(int parentYorumId);
    List<YeniYorum> findByKullaniciId_KullaniciId(int kullaniciId);

}