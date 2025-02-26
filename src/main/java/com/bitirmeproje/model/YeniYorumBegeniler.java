package com.bitirmeproje.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "yeni_yorum_begeniler")
public class YeniYorumBegeniler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yeni_yorum_begeni_id")
    private int yorumBegenilerId;

    @ManyToOne
    @JoinColumn(name = "yeni_yorum_id")
    private YeniYorum yeniYorumId;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private User kullaniciId;

    @Column(name = "begenme_zamani")
    private LocalDate begenmeZamani;

    public int getYorumBegenilerId() {
        return yorumBegenilerId;
    }

    public void setYorumBegenilerId(int yorumBegenilerId) {
        this.yorumBegenilerId = yorumBegenilerId;
    }

    public YeniYorum getYorumId() {
        return yeniYorumId;
    }

    public void setYorumId(YeniYorum yorumId) {
        this.yeniYorumId = yorumId;
    }

    public User getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(User kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public LocalDate getBegenmeZamani() {
        return begenmeZamani;
    }

    public void setBegenmeZamani(LocalDate begenmeZamani) {
        this.begenmeZamani = begenmeZamani;
    }

    public YeniYorum getYeniYorumId() {
        return yeniYorumId;
    }

    public void setYeniYorumId(YeniYorum yeniYorumId) {
        this.yeniYorumId = yeniYorumId;
    }
}
