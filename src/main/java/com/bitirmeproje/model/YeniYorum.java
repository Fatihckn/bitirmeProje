package com.bitirmeproje.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "yeni_yorum")
public class YeniYorum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yeni_yorum_id")
    private int yorumId;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private User kullaniciId;

    @ManyToOne
    @JoinColumn(name = "gonderi_id")
    private Gonderiler gonderiId;

    @Column(name = "yeni_yorum_icerigi")
    private String yeniYorumIcerigi;

    @Column(name = "yeni_yorum_begeni_sayisi")
    private int yeniYorumBegeniSayisi;

    @Column(name = "yeni_yorum_olusturulma_tarihi")
    private LocalDate yeniYorumOlusturulmaTarihi;

    @OneToMany(mappedBy = "yeniYorumId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YeniYorumBegeniler> yeniYorumBegeniler;

    @OneToMany(mappedBy = "yeniYorumId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YorumTakip> yorumTakip;

    public int getYorumId() {
        return yorumId;
    }

    public void setYorumId(int yorumId) {
        this.yorumId = yorumId;
    }

    public User getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(User kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public Gonderiler getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(Gonderiler gonderiId) {
        this.gonderiId = gonderiId;
    }

    public String getYeniYorumIcerigi() {
        return yeniYorumIcerigi;
    }

    public void setYeniYorumIcerigi(String yeniYorumIcerigi) {
        this.yeniYorumIcerigi = yeniYorumIcerigi;
    }

    public int getYeniYorumBegeniSayisi() {
        return yeniYorumBegeniSayisi;
    }

    public void setYeniYorumBegeniSayisi(int yeniYorumBegeniSayisi) {
        this.yeniYorumBegeniSayisi = yeniYorumBegeniSayisi;
    }

    public LocalDate getYeniYorumOlusturulmaTarihi() {
        return yeniYorumOlusturulmaTarihi;
    }

    public void setYeniYorumOlusturulmaTarihi(LocalDate yeniYorumOlusturulmaTarihi) {
        this.yeniYorumOlusturulmaTarihi = yeniYorumOlusturulmaTarihi;
    }

    public List<YeniYorumBegeniler> getYeniYorumBegeniler() {
        return yeniYorumBegeniler;
    }

    public void setYeniYorumBegeniler(List<YeniYorumBegeniler> yeniYorumBegeniler) {
        this.yeniYorumBegeniler = yeniYorumBegeniler;
    }

    public List<YorumTakip> getYorumTakip() {
        return yorumTakip;
    }

    public void setYorumTakip(List<YorumTakip> yorumTakip) {
        this.yorumTakip = yorumTakip;
    }
}
