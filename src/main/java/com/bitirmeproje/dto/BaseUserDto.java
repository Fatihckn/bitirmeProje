package com.bitirmeproje.dto;

import java.time.LocalDate;

public abstract class BaseUserDto {
    protected String kullaniciTakmaAd;
    protected String ePosta;
    protected String kullaniciBio;
    protected String kullaniciProfilResmi;
    protected String kullaniciTelefonNo;
    protected LocalDate kullaniciDogumTarihi;
    protected LocalDate kullaniciUyeOlmaTarihi;

    public BaseUserDto() {}

    public BaseUserDto(String kullaniciTakmaAd, String ePosta, String kullaniciBio, String kullaniciProfilResmi, String kullaniciTelefonNo, LocalDate kullaniciDogumTarihi, LocalDate kullaniciUyeOlmaTarihi) {
        this.kullaniciTakmaAd = kullaniciTakmaAd;
        this.ePosta = ePosta;
        this.kullaniciBio = kullaniciBio;
        this.kullaniciProfilResmi = kullaniciProfilResmi;
        this.kullaniciTelefonNo = kullaniciTelefonNo;
        this.kullaniciDogumTarihi = kullaniciDogumTarihi;
        this.kullaniciUyeOlmaTarihi = kullaniciUyeOlmaTarihi;
    }

    // Getter ve Setter'lar
    public String getKullaniciTakmaAd() {
        return kullaniciTakmaAd;
    }

    public void setKullaniciTakmaAd(String kullaniciTakmaAd) {
        this.kullaniciTakmaAd = kullaniciTakmaAd;
    }

    public String getePosta() {
        return ePosta;
    }

    public void setePosta(String ePosta) {
        this.ePosta = ePosta;
    }

    public String getKullaniciBio() {
        return kullaniciBio;
    }

    public void setKullaniciBio(String kullaniciBio) {
        this.kullaniciBio = kullaniciBio;
    }

    public String getKullaniciProfilResmi() {
        return kullaniciProfilResmi;
    }

    public void setKullaniciProfilResmi(String kullaniciProfilResmi) {
        this.kullaniciProfilResmi = kullaniciProfilResmi;
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
}
