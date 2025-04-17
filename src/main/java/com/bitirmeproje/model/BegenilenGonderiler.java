package com.bitirmeproje.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "begenilen_gonderiler")
public class BegenilenGonderiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "begenilen_gonderiler_id")
    private int begenilenGonderilerId;

    @ManyToOne
    @JoinColumn(name = "gonderi_id")
    private Gonderiler gonderiId;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private User kullaniciId;

    @Column(name = "begenme_zamani")
    private LocalDateTime begenmeZamani;

    public int getBegenilenGonderilerId() {
        return begenilenGonderilerId;
    }

    public void setBegenilenGonderilerId(int begenilenGonderilerId) {
        this.begenilenGonderilerId = begenilenGonderilerId;
    }

    public Gonderiler getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(Gonderiler gonderiId) {
        this.gonderiId = gonderiId;
    }

    public User getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(User kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public LocalDateTime getBegenmeZamani() {
        return begenmeZamani;
    }

    public void setBegenmeZamani(LocalDateTime begenmeZamani) {
        this.begenmeZamani = begenmeZamani;
    }

    @PrePersist
    protected void onCreate() {
        if (this.begenmeZamani == null) {
            this.begenmeZamani = LocalDateTime.now();
        }
    }
}
