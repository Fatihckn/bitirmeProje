package com.bitirmeproje.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cevap_yorum")
public class CevapYorum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cevap_yorum_id")
    private int cevapYorumId;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private User kullaniciId;

    @Column(name = "cevap_yorum_icerik")
    private String cevapYorumIcerik;

    @Column(name = "cevap_yorum_begeni_sayisi")
    private int begeniSayisi;

    @Column(name = "cevap_yorum_olusturulma_tarihi")
    private LocalDate olusturulmaTarihi;

    @OneToMany(mappedBy = "cevapYorumId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CevapYorumBegeniler> cevapYorumBegeniler;

    @OneToMany(mappedBy = "cevapYorumId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YorumTakip> cevapYorumTakip;

    public int getCevapYorumId() {
        return cevapYorumId;
    }

    public void setCevapYorumId(int cevapYorumId) {
        this.cevapYorumId = cevapYorumId;
    }

    public User getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(User kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getCevapYorumIcerik() {
        return cevapYorumIcerik;
    }

    public void setCevapYorumIcerik(String cevapYorumIcerik) {
        this.cevapYorumIcerik = cevapYorumIcerik;
    }

    public int getBegeniSayisi() {
        return begeniSayisi;
    }

    public void setBegeniSayisi(int begeniSayisi) {
        this.begeniSayisi = begeniSayisi;
    }

    public LocalDate getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }

    public void setOlusturulmaTarihi(LocalDate olusturulmaTarihi) {
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public List<CevapYorumBegeniler> getCevapYorumBegeniler() {
        return cevapYorumBegeniler;
    }

    public void setCevapYorumBegeniler(List<CevapYorumBegeniler> cevapYorumBegeniler) {
        this.cevapYorumBegeniler = cevapYorumBegeniler;
    }

    public List<YorumTakip> getCevapYorumTakip() {
        return cevapYorumTakip;
    }

    public void setCevapYorumTakip(List<YorumTakip> cevapYorumTakip) {
        this.cevapYorumTakip = cevapYorumTakip;
    }
}
