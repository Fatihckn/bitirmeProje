package com.bitirmeproje.repository;

import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.model.YeniYorumBegeniler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YeniYorumBegenilerRepository extends JpaRepository<YeniYorumBegeniler, Integer> {

    boolean existsByYeniYorumAndKullanici(YeniYorum yeniYorum, User kullanici);

    Optional<YeniYorumBegeniler> findByYeniYorumAndKullanici(YeniYorum yeniYorum, User kullanici);

    int countByYeniYorum(YeniYorum yeniYorum);

    List<YeniYorumBegeniler> findByKullanici_KullaniciId(int kullaniciId);
}