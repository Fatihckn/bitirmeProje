package com.bitirmeproje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "anketler")
public class Anketler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anket_id")
    private int anketId;

    @Column(name = "anket_sorusu")
    private String anketSorusu;

    @Column(name = "olusturulma_tarihi")
    private LocalDateTime olusturulmaTarihi;

    @OneToMany(mappedBy = "anketId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Secenekler> secenekler;

    @OneToMany(mappedBy = "anketId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cevaplar> cevaplar;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "kullanici_id")
    private User kullanici;

    public int getAnketId() {
        return anketId;
    }

    public void setAnketId(int anketId) {
        this.anketId = anketId;
    }

    public String getAnketSorusu() {
        return anketSorusu;
    }

    public void setAnketSorusu(String anketSorusu) {
        this.anketSorusu = anketSorusu;
    }

    public LocalDateTime getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }

    public void setOlusturulmaTarihi(LocalDateTime olusturulmaTarihi) {
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public List<Secenekler> getSecenekler() {
        return secenekler;
    }

    public void setSecenekler(List<Secenekler> secenekler) {
        this.secenekler = secenekler;
    }

    public List<Cevaplar> getCevaplar() {
        return cevaplar;
    }

    public void setCevaplar(List<Cevaplar> cevaplar) {
        this.cevaplar = cevaplar;
    }

    public User getKullanici() {
        return kullanici;
    }

    public void setKullanici(User kullanici) {
        this.kullanici = kullanici;
    }
}
