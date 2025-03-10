package com.bitirmeproje.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "gonderiler")
public class Gonderiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Eğer otomatik artan ise
    @Column(name = "gonderi_id", nullable = false)
    private int gonderiId;

    @Column(name = "gonderi_icerigi", nullable = false)
    private String gonderiIcerigi;

    @Column(name = "gonderi_tarihi")
    private LocalDate gonderiTarihi;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "gonderi_begeni_sayisi", nullable = true)
    private Integer gonderiBegeniSayisi;

    @ManyToOne // Bir kullanıcı birden fazla gönderi oluşturabilir
    @JoinColumn(name = "kullanici_id", nullable = false) // Foreign key belirleme
    private User kullaniciId; // User tablosu ile ilişkilendirme

    @OneToMany(mappedBy = "gonderiId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BegenilenGonderiler> begenilenGonderiler;

    @OneToMany(mappedBy = "gonderiId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YeniYorum> yeniYorum;

    // Getter ve Setter Metotları
    public int getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(int gonderiId) {
        this.gonderiId = gonderiId;
    }

    public String getGonderiIcerigi() {
        return gonderiIcerigi;
    }

    public void setGonderiIcerigi(String gonderiIcerigi) {
        this.gonderiIcerigi = gonderiIcerigi;
    }

    public LocalDate getGonderiTarihi() {
        return gonderiTarihi;
    }

    public void setGonderiTarihi(LocalDate gonderiTarihi) {
        this.gonderiTarihi = gonderiTarihi;
    }

    public int getGonderiBegeniSayisi() {
        return gonderiBegeniSayisi != null ? gonderiBegeniSayisi : 0;
    }

    public void setGonderiBegeniSayisi(int gonderiBegeniSayisi) {
        this.gonderiBegeniSayisi = gonderiBegeniSayisi;
    }

    public User getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(User kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public List<BegenilenGonderiler> getBegenilenGonderiler() {
        return begenilenGonderiler;
    }

    public void setBegenilenGonderiler(List<BegenilenGonderiler> begenilenGonderiler) {
        this.begenilenGonderiler = begenilenGonderiler;
    }

    public List<YeniYorum> getYeniYorum() {
        return yeniYorum;
    }

    public void setYeniYorum(List<YeniYorum> yeniYorum) {
        this.yeniYorum = yeniYorum;
    }
}
