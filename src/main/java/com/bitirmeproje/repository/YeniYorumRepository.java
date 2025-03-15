package com.bitirmeproje.repository;

import com.bitirmeproje.model.YeniYorum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YeniYorumRepository extends JpaRepository<YeniYorum, Integer> {

    // Belirli bir gönderiye ait yorumları getir
    List<YeniYorum> findByGonderiId_GonderiId(int gonderiId);

    // Belirli bir üst yoruma ait alt yorumları getir
    List<YeniYorum> findByParentYorum_YorumId(int parentYorumId);

    List<YeniYorum> findByKullaniciId_KullaniciId(int kullaniciId);

    @Query("SELECT y FROM YeniYorum y WHERE y.parentYorum.yorumId = :parentYorumId")
    YeniYorum findYeniYorumByParentYorum(@Param("parentYorumId") int parentYorum);
}