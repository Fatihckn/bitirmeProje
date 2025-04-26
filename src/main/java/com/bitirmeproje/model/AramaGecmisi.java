package com.bitirmeproje.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "arama_gecmisi")
public class AramaGecmisi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arama_gecmisi_id")
    private int aramaGecmisiId;

    @Column(name = "aranan_kullanici_id")
    private int arananKullaniciId;

    @Column(name = "arama_zamani")
    private LocalDateTime aramaZamani;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private User kullaniciId;

    public int getAramaGecmisiId() {
        return aramaGecmisiId;
    }

    public void setAramaGecmisiId(int aramaGecmisiId) {
        this.aramaGecmisiId = aramaGecmisiId;
    }

    public int getArananKullaniciId() {
        return arananKullaniciId;
    }

    public void setArananKullaniciId(int arananKullaniciId) {
        this.arananKullaniciId = arananKullaniciId;
    }

    public LocalDateTime getAramaZamani() {
        return aramaZamani;
    }

    public void setAramaZamani(LocalDateTime aramaZamani) {
        this.aramaZamani = aramaZamani;
    }

    public User getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(User kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    @PrePersist
    protected void onCreate() {
        if (this.aramaZamani == null) {
            this.aramaZamani = LocalDateTime.now();
        }
    }
}
