package com.bitirmeproje.dto.home;

import java.time.LocalDateTime;

public class HomeDto {

    private int gonderiId;

    private int kullaniciId;

    private String gonderiIcerigi;

    private int begeniSayisi;

    private LocalDateTime gonderiTarihi;

    private String takipEdilenKullaniciTakmaAd;

    private Boolean begenildiMi;

    private String kullaniciResim;

    private String gonderiMedyaUrl;

    private long gonderiYorumSayisi;

    public HomeDto() {}

    public HomeDto(int gonderiId, int kullaniciId, String gonderiIcerigi, int begeniSayisi,
                   LocalDateTime gonderiTarihi, String takipEdilenKullaniciTakmaAd, String kullaniciResim,
                   String gonderiMedyaUrl, boolean begenildiMi, long gonderiYorumSayisi) {
        this.gonderiId = gonderiId;
        this.kullaniciId = kullaniciId;
        this.gonderiIcerigi = gonderiIcerigi;
        this.begeniSayisi = begeniSayisi;
        this.gonderiTarihi = gonderiTarihi;
        this.takipEdilenKullaniciTakmaAd = takipEdilenKullaniciTakmaAd;
        this.begenildiMi = begenildiMi;
        this.gonderiMedyaUrl = gonderiMedyaUrl;
        this.kullaniciResim = kullaniciResim;
        this.gonderiYorumSayisi = gonderiYorumSayisi;
    }

    public int getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(int gonderiId) {
        this.gonderiId = gonderiId;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getGonderiIcerigi() {
        return gonderiIcerigi;
    }

    public void setGonderiIcerigi(String gonderiIcerigi) {
        this.gonderiIcerigi = gonderiIcerigi;
    }

    public int getBegeniSayisi() {
        return begeniSayisi;
    }

    public void setBegeniSayisi(int begeniSayisi) {
        this.begeniSayisi = begeniSayisi;
    }

    public LocalDateTime getGonderiTarihi() {
        return gonderiTarihi;
    }

    public void setGonderiTarihi(LocalDateTime gonderiTarihi) {
        this.gonderiTarihi = gonderiTarihi;
    }

    public String getTakipEdilenKullaniciTakmaAd() {
        return takipEdilenKullaniciTakmaAd;
    }

    public void setTakipEdilenKullaniciTakmaAd(String takipEdilenKullaniciTakmaAd) {
        this.takipEdilenKullaniciTakmaAd = takipEdilenKullaniciTakmaAd;
    }

    public Boolean getBegenildiMi() {
        return begenildiMi;
    }

    public void setBegenildiMi(Boolean begenildiMi) {
        this.begenildiMi = begenildiMi;
    }

    public String getKullaniciResim() {
        return kullaniciResim;
    }

    public void setKullaniciResim(String kullaniciResim) {
        this.kullaniciResim = kullaniciResim;
    }

    public String getGonderiMedyaUrl() {
        return gonderiMedyaUrl;
    }

    public void setGonderiMedyaUrl(String gonderiMedyaUrl) {
        this.gonderiMedyaUrl = gonderiMedyaUrl;
    }

    public long getGonderiYorumSayisi() {
        return gonderiYorumSayisi;
    }

    public void setGonderiYorumSayisi(long gonderiYorumSayisi) {
        this.gonderiYorumSayisi = gonderiYorumSayisi;
    }
}
