package com.bitirmeproje.repository;

import com.bitirmeproje.dto.UserDto;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByKullaniciId(int kullaniciId);

    Optional<User> findByEPosta(String ePosta);

    List<User> findAll();

    @Query(value = "SELECT u.kullanici_takma_ad FROM kullanicilar u", nativeQuery = true)
    List<String> findAllByKullaniciTakmaAd();

    Optional<User> findByKullaniciTakmaAd(String kullaniciTakmaAd);

    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.kullaniciTakmaAd) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(u.ePosta) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR CAST(u.kullaniciId AS string) LIKE CONCAT('%', :query, '%')")
    List<User> searchByQuery(@Param("query") String query);


}
