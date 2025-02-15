package com.bitirmeproje.repository;

import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KullaniciRepository extends JpaRepository<User, Integer> { Optional<User> findByKullaniciEPosta(String kullaniciEPosta);}
