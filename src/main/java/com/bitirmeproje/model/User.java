package com.bitirmeproje.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "kullanici")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KullaniciID")  // ✅ Veritabanındaki adı birebir eşleştiriyoruz
    private int kullaniciID;

    @Column(name = "KullaniciEPosta", nullable = false, unique = true, length = 100)
    private String kullaniciEPosta;

    @Column(name = "KullaniciSifre", nullable = false, length = 50)
    private String kullaniciSifre;

    @Column(name = "KullaniciTakmaAd", nullable = false, length = 15)
    private String kullaniciTakmaAd;

    @Column(name = "KullaniciProfilResmi", columnDefinition = "VARCHAR(MAX)")
    private String kullaniciProfilResmi;

    @Column(name = "KullaniciBio", length = 1000)
    private String kullaniciBio;

    @Column(name = "KullaniciTelefonNo", length = 11)
    private String kullaniciTelefonNo;

    @Column(name = "KullaniciCinsiyet", length = 10)
    private String kullaniciCinsiyet;

    @Column(name = "KullaniciDogumTarihi")
    private LocalDate kullaniciDogumTarihi;


    public String getKullaniciEPosta() {
        return kullaniciEPosta;
    }
    public void setKullaniciEPosta(String kullaniciEPosta) {
        this.kullaniciEPosta = kullaniciEPosta;
    }
    public String getKullaniciSifre() {
        return kullaniciSifre;
    }
    public void setKullaniciSifre(String kullaniciSifre) {
        this.kullaniciSifre = kullaniciSifre;
    }
}
