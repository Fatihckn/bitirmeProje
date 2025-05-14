package com.bitirmeproje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cevaplar")
public class Cevaplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cevap_id")
    private int cevapId;

    @Column(name = "cevap_tarihi")
    private LocalDateTime cevapTarih;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "anket_id")
    private Anketler anketId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "kullanici_id")
    private User kullaniciId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "secenek_id")
    private Secenekler secenekId;

    public int getCevapId() {
        return cevapId;
    }

    public void setCevapId(int cevapId) {
        this.cevapId = cevapId;
    }

    public LocalDateTime getCevapTarih() {
        return cevapTarih;
    }

    public void setCevapTarih(LocalDateTime cevapTarih) {
        this.cevapTarih = cevapTarih;
    }

    public Anketler getAnketId() {
        return anketId;
    }

    public void setAnketId(Anketler anketId) {
        this.anketId = anketId;
    }

    public User getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(User kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public Secenekler getSecenekId() {
        return secenekId;
    }

    public void setSecenekId(Secenekler secenekId) {
        this.secenekId = secenekId;
    }
}
