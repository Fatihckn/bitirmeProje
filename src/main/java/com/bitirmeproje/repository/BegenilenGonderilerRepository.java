package com.bitirmeproje.repository;

import com.bitirmeproje.model.BegenilenGonderiler;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BegenilenGonderilerRepository extends JpaRepository<BegenilenGonderiler,Integer> {

    List<BegenilenGonderiler> findByKullaniciId(User kullanici);//Belirli bir kullanıcının beğendiği gönderileri getir

    boolean existsByGonderiIdAndKullaniciId(Gonderiler gonderiler,User kullanici);//spesifik kullanıcı spesifik gönderiyi beğendi mi

    void deleteByGonderiIdAndKullaniciId(Gonderiler gonderi,User kullanici);//beğeni kaldır

    /*@Query("SELECT b.gonderiId FROM BegenilenGonderiler b GROUP BY b.gonderiId ORDER BY COUNT(b.gonderiId) DESC")
    List<Gonderiler> findTop10ByOrderByBegeniSayisi();*/

}
