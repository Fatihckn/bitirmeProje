package com.bitirmeproje.dto;

import java.time.LocalDate;

public class UserDto {

    private int kullaniciId;

    private String kullaniciTakmaAd;

    private String ePosta;

    private String kullaniciBio;

    private String kullaniciProfilResmi;

    private String kullaniciTelefonNo;

    private LocalDate kullaniciDogumTarihi;

    private LocalDate kullaniciUyeOlmaTarihi;

    public int getKullaniciId() {
        return kullaniciId;
    }

    public UserDto(){}

    public UserDto(int kullaniciId, String kullaniciTakmaAd, String ePosta, String kullaniciBio, String kullaniciProfilResmi, String kullaniciTelefonNo, LocalDate kullaniciDogumTarihi, LocalDate kullaniciUyeOlmaTarihi) {
        this.kullaniciId = kullaniciId;
        this.kullaniciTakmaAd = kullaniciTakmaAd;
        this.ePosta = ePosta;
        this.kullaniciBio = kullaniciBio;
        this.kullaniciProfilResmi = kullaniciProfilResmi;
        this.kullaniciTelefonNo = kullaniciTelefonNo;
        this.kullaniciDogumTarihi = kullaniciDogumTarihi;
        this.kullaniciUyeOlmaTarihi = kullaniciUyeOlmaTarihi;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

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
