package com.bitirmeproje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "gonderiler")
public class Gonderiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Eğer otomatik artan ise
    @Column(name = "gonderi_id", nullable = false)
    private int gonderiId;

    @Column(name = "gonderi_icerigi")
    private String gonderiIcerigi;

    @Column(name = "gonderi_tarihi")
    private LocalDateTime gonderiTarihi;

    @Column(name = "gonderi_begeni_sayisi", nullable = false)
    private Integer gonderiBegeniSayisi = 0;

    @Column(name = "gonderi_medya_url")
    private String gonderiMedyaUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "gonderi_medya_turu")
    private MedyaTuru gonderiMedyaTuru;

    @JsonBackReference
    @ManyToOne // Bir kullanıcı birden fazla gönderi oluşturabilir
    @JoinColumn(name = "kullanici_id", nullable = false) // Foreign key belirleme
    private User kullaniciId; // User tablosu ile ilişkilendirme

    @OneToMany(mappedBy = "gonderiId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BegenilenGonderiler> begenilenGonderiler;

    @OneToMany(mappedBy = "gonderiId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YeniYorum> yeniYorum;

    public Gonderiler() {}

    public Gonderiler(int gonderiId) {this.gonderiId = gonderiId;}

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

    public LocalDateTime getGonderiTarihi() {
        return gonderiTarihi;
    }

    public void setGonderiTarihi(LocalDateTime gonderiTarihi) {
        this.gonderiTarihi = gonderiTarihi;
    }

    public int getGonderiBegeniSayisi() {
        return gonderiBegeniSayisi;
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

    public void setGonderiBegeniSayisi(Integer gonderiBegeniSayisi) {
        this.gonderiBegeniSayisi = gonderiBegeniSayisi;
    }

    public String getGonderiMedyaUrl() {
        return this.gonderiMedyaUrl;
    }

    public void setGonderiMedyaUrl(String gonderiMedyaUrl) {
        this.gonderiMedyaUrl = gonderiMedyaUrl;
    }

    public MedyaTuru getGonderiMedyaTuru() {
        return this.gonderiMedyaTuru;
    }

    public void setGonderiMedyaTuru(MedyaTuru gonderiMedyaTuru) {
        this.gonderiMedyaTuru = gonderiMedyaTuru;
    }
}

