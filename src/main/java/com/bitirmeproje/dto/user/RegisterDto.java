package com.bitirmeproje.dto.user;

import java.time.LocalDate;

public class RegisterDto {
    private String ePosta;

    private String sifre;

    private String kullaniciTakmaAd;

    private String kullaniciTelefonNo;

    private LocalDate kullaniciDogumTarihi;

    private String kullaniciCinsiyet;

    private String kullaniciUyeUlkesi;

    public RegisterDto(String ePosta, String sifre, String kullaniciTakmaAd, String kullaniciTelefonNo, LocalDate kullaniciDogumTarihi, String kullaniciCinsiyet, String kullaniciUyeUlkesi) {
        this.ePosta = ePosta;
        this.sifre = sifre;
        this.kullaniciTakmaAd = kullaniciTakmaAd;
        this.kullaniciTelefonNo = kullaniciTelefonNo;
        this.kullaniciDogumTarihi = kullaniciDogumTarihi;
        this.kullaniciCinsiyet = kullaniciCinsiyet;
        this.kullaniciUyeUlkesi = kullaniciUyeUlkesi;
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
}
