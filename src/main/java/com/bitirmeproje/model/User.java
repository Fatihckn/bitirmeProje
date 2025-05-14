package com.bitirmeproje.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "kullanicilar")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kullanici_id")
    private int kullaniciId;

    @Column(name = "kullanici_eposta", unique = true)
    private String ePosta;

    @Column(name = "kullanici_sifre")
    private String sifre;

    @Column(name = "kullanici_takma_ad", unique = true)
    private String kullaniciTakmaAd;

    @Column(name = "kullanici_profil_resmi")
    private String kullaniciProfilResmi;

    @Column(name = "kullanici_bio")
    private String kullaniciBio;

    @Column(name = "kullanici_telefon_no", unique = true)
    private String kullaniciTelefonNo;

    @Column(name = "kullanici_dogum_tarihi")
    private LocalDate kullaniciDogumTarihi;

    @Column(name = "kullanici_uye_olma_tarihi")
    private LocalDate kullaniciUyeOlmaTarihi;

    @Enumerated(EnumType.STRING)
    @Column(name = "kullanici_rol")
    private Role kullaniciRole;

    @Column(name = "kullanici_cinsiyet")
    private String kullaniciCinsiyet;

    @Column(name = "kullanici_uye_ulkesi")
    private String kullaniciUyeUlkesi;

    @JsonManagedReference
    @OneToMany(mappedBy = "kullaniciId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gonderiler> gonderiler; // Kullanıcının gönderileri

    @OneToMany(mappedBy = "takipEdenKullaniciId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follows> takipEdenKullaniciFollows;

    @OneToMany(mappedBy = "takipEdilenKullaniciId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follows> takipEdilenKullaniciFollows;

    @OneToMany(mappedBy = "kullaniciId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YeniYorum> yeniYorum;

    @OneToMany(mappedBy = "kullaniciId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AramaGecmisi> aramaGecmisi;

    @OneToMany(mappedBy = "kullaniciId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BegenilenGonderiler> begenilenGonderiler;

    @OneToMany(mappedBy = "kullanici", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YeniYorumBegeniler> yeniYorumBegeniler;

    @OneToMany(mappedBy = "mesajGonderilenKullaniciId",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mesaj> mesajGonderilenKullaniciId;

    @OneToMany(mappedBy = "mesajGonderenKullaniciId",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mesaj> mesajGonderenKullaniciId;

    @OneToMany(mappedBy = "kullaniciId",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cevaplar> kullaniciIdCevaplar;

    @OneToMany(mappedBy = "kullanici",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Anketler> kullaniciAnketler;

    public User() {}

    public User(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public <T> User(String email, String s, List<T> ts) {
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getePosta() {
        return ePosta;
    }

    public void setePosta(String ePosta) {
        this.ePosta = ePosta;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getKullaniciTakmaAd() {
        return kullaniciTakmaAd;
    }

    public void setKullaniciTakmaAd(String kullaniciTakmaAd) {
        this.kullaniciTakmaAd = kullaniciTakmaAd;
    }

    public String getKullaniciProfilResmi() {
        return kullaniciProfilResmi;
    }

    public void setKullaniciProfilResmi(String kullaniciProfilResmi) {
        this.kullaniciProfilResmi = kullaniciProfilResmi;
    }

    public String getKullaniciBio() {
        return kullaniciBio;
    }

    public void setKullaniciBio(String kullaniciBio) {
        this.kullaniciBio = kullaniciBio;
    }

    public String getKullaniciTelefonNo() {
        return kullaniciTelefonNo;
    }

    public void setKullaniciTelefonNo(String kullaniciTelefonNo) {
        this.kullaniciTelefonNo = kullaniciTelefonNo;
    }

    public LocalDate getKullaniciDogumTarihi() {
        return kullaniciDogumTarihi;
    }

    public void setKullaniciDogumTarihi(LocalDate kullaniciDogumTarihi) {
        this.kullaniciDogumTarihi = kullaniciDogumTarihi;
    }

    public LocalDate getKullaniciUyeOlmaTarihi() {
        return kullaniciUyeOlmaTarihi;
    }

    public void setKullaniciUyeOlmaTarihi(LocalDate kullaniciUyeOlmaTarihi) {
        this.kullaniciUyeOlmaTarihi = kullaniciUyeOlmaTarihi;
    }

    public Role getKullaniciRole() {
        return kullaniciRole;
    }

    public void setKullaniciRole(Role kullaniciRole) {
        this.kullaniciRole = kullaniciRole;
    }

    public List<Gonderiler> getGonderiler() {
        return gonderiler;
    }

    public void setGonderiler(List<Gonderiler> gonderiler) {
        this.gonderiler = gonderiler;
    }

    public List<Follows> getTakipEdenKullaniciFollows() {
        return takipEdenKullaniciFollows;
    }

    public void setTakipEdenKullaniciFollows(List<Follows> takipEdenKullaniciFollows) {
        this.takipEdenKullaniciFollows = takipEdenKullaniciFollows;
    }

    public List<Follows> getTakipEdilenKullaniciFollows() {
        return takipEdilenKullaniciFollows;
    }

    public void setTakipEdilenKullaniciFollows(List<Follows> takipEdilenKullaniciFollows) {
        this.takipEdilenKullaniciFollows = takipEdilenKullaniciFollows;
    }

    public List<YeniYorum> getYeniYorum() {
        return yeniYorum;
    }

    public void setYeniYorum(List<YeniYorum> yeniYorum) {
        this.yeniYorum = yeniYorum;
    }

    public List<AramaGecmisi> getAramaGecmisi() {
        return aramaGecmisi;
    }

    public void setAramaGecmisi(List<AramaGecmisi> aramaGecmisi) {
        this.aramaGecmisi = aramaGecmisi;
    }

    public List<BegenilenGonderiler> getBegenilenGonderiler() {
        return begenilenGonderiler;
    }

    public void setBegenilenGonderiler(List<BegenilenGonderiler> begenilenGonderiler) {
        this.begenilenGonderiler = begenilenGonderiler;
    }

    public List<YeniYorumBegeniler> getYeniYorumBegeniler() {
        return yeniYorumBegeniler;
    }

    public void setYeniYorumBegeniler(List<YeniYorumBegeniler> yeniYorumBegeniler) {
        this.yeniYorumBegeniler = yeniYorumBegeniler;
    }

    public List<Mesaj> getMesajGonderilenKullaniciId() {
        return mesajGonderilenKullaniciId;
    }

    public void setMesajGonderilenKullaniciId(List<Mesaj> mesajGonderilenKullaniciId) {
        this.mesajGonderilenKullaniciId = mesajGonderilenKullaniciId;
    }

    public List<Mesaj> getMesajGonderenKullaniciId() {
        return mesajGonderenKullaniciId;
    }

    public void setMesajGonderenKullaniciId(List<Mesaj> mesajGonderenKullaniciId) {
        this.mesajGonderenKullaniciId = mesajGonderenKullaniciId;
    }

    public String getKullaniciCinsiyet() {
        return kullaniciCinsiyet;
    }

    public void setKullaniciCinsiyet(String kullaniciCinsiyet) {
        this.kullaniciCinsiyet = kullaniciCinsiyet;
    }

    public String getKullaniciUyeUlkesi() {
        return kullaniciUyeUlkesi;
    }

    public void setKullaniciUyeUlkesi(String kullaniciUyeUlkesi) {
        this.kullaniciUyeUlkesi = kullaniciUyeUlkesi;
    }

    public List<Cevaplar> getKullaniciIdCevaplar() {
        return kullaniciIdCevaplar;
    }

    public void setKullaniciIdCevaplar(List<Cevaplar> kullaniciIdCevaplar) {
        this.kullaniciIdCevaplar = kullaniciIdCevaplar;
    }

    @PrePersist
    protected void onCreate() {
        if (this.kullaniciUyeOlmaTarihi == null) {
            this.kullaniciUyeOlmaTarihi = LocalDate.now();
        }
    }
}
