package com.bitirmeproje.repository;

import com.bitirmeproje.model.Anketler;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnketlerRepository extends JpaRepository<Anketler, Integer> {

    List<Anketler> findAnketlerByKullaniciOrderByOlusturulmaTarihiDesc(User kullanici);
}
