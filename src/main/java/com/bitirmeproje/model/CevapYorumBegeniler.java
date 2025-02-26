package com.bitirmeproje.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "cevap_yorum_begeniler")
public class CevapYorumBegeniler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cevap_yorum_begeniler_id")
    private int cevapYorumBegenilerId;

    @ManyToOne
    @JoinColumn(name = "cevap_yorum_id")
    private CevapYorum cevapYorumId;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private User kullaniciId;

    @Column(name = "begenma_zamani")
    private LocalDate begenmeZamani;

    public int getCevapYorumBegenilerId() {
        return cevapYorumBegenilerId;
    }

    public void setCevapYorumBegenilerId(int cevapYorumBegenilerId) {
        this.cevapYorumBegenilerId = cevapYorumBegenilerId;
    }

    public CevapYorum getCevapYorumId() {
        return cevapYorumId;
    }

    public void setCevapYorumId(CevapYorum cevapYorumId) {
        this.cevapYorumId = cevapYorumId;
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
}
