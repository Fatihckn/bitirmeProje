package com.bitirmeproje.repository;

import com.bitirmeproje.model.Cevaplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CevaplarRepository extends JpaRepository<Cevaplar, Integer> {
}
