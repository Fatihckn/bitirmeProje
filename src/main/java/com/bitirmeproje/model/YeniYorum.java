package com.bitirmeproje.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JoinColumn(name = "kullanici_id", nullable = false)
    private User kullaniciId; // User Modeline Uygun

    @ManyToOne
    @JoinColumn(name = "gonderi_id", nullable = true) // Eğer bir gönderiye yapılan yorumsa dolu olacak
    private Gonderiler gonderiId; // Gonderiler Modeline Uygun

    @Column(name = "yorum_icerigi", nullable = false)
    private String yeniYorumIcerigi;

    @Column(name = "yorum_tarihi", nullable = false)
    private LocalDate yeniYorumOlusturulmaTarihi;

    @Column(name = "begeni_sayisi", nullable = false)
    private int yeniYorumBegeniSayisi = 0; // Varsayılan değer sıfır olsun

    @ManyToOne
    @JoinColumn(name = "parent_yorum_id", nullable = true) // Eğer bir başka yoruma yapılan alt yorumsa
    private YeniYorum parentYorum;

    @OneToMany(mappedBy = "parentYorum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YeniYorum> altYorumlar;

    @OneToMany(mappedBy = "yeniYorum", cascade = CascadeType.ALL, orphanRemoval = true) // *yeniYorumId değil yeniYorum olacak*
    private List<YeniYorumBegeniler> yeniYorumBegeniler;

    // Getter ve Setter metotları
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

    public LocalDate getYeniYorumOlusturulmaTarihi() {
        return yeniYorumOlusturulmaTarihi;
    }

    public void setYeniYorumOlusturulmaTarihi(LocalDate yeniYorumOlusturulmaTarihi) {
        this.yeniYorumOlusturulmaTarihi = yeniYorumOlusturulmaTarihi;
    }

    public int getYeniYorumBegeniSayisi() {
        return yeniYorumBegeniSayisi;
    }

    public void setYeniYorumBegeniSayisi(int yeniYorumBegeniSayisi) {
        this.yeniYorumBegeniSayisi = yeniYorumBegeniSayisi;
    }

    public YeniYorum getParentYorum() {
        return parentYorum;
    }

    public void setParentYorum(YeniYorum parentYorum) {
        this.parentYorum = parentYorum;
    }

    public List<YeniYorum> getAltYorumlar() {
        return altYorumlar;
    }

    public void setAltYorumlar(List<YeniYorum> altYorumlar) {
        this.altYorumlar = altYorumlar;
    }

    public List<YeniYorumBegeniler> getYeniYorumBegeniler() {
        return yeniYorumBegeniler;
    }

    public void setYeniYorumBegeniler(List<YeniYorumBegeniler> yeniYorumBegeniler) {
        this.yeniYorumBegeniler = yeniYorumBegeniler;
    }
}