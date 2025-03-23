package com.bitirmeproje.dto.home;

import java.time.LocalDate;

public class HomeDto {

    private int gonderiId;

    private int kullaniciId;

    private String gonderiIcerigi;

    private int begeniSayisi;

    private LocalDate gonderiTarihi;

    private String takipEdilenKullaniciTakmaAd;

    private Boolean begenildiMi;

    private String kullaniciResim;

    public HomeDto() {}

//    public HomeDto(int gonderiId, int kullaniciId, String gonderiIcerigi, int gonderiBegeniSayisi, LocalDate gonderiTarihi, String takipEdilenKullaniciTakmaAd) {
//        this.gonderiId = gonderiId;
//        this.kullaniciId = kullaniciId;
//        this.gonderiIcerigi = gonderiIcerigi;
//        this.gonderiTarihi = gonderiTarihi;
//        this.begeniSayisi = gonderiBegeniSayisi;
//        this.takipEdilenKullaniciTakmaAd = takipEdilenKullaniciTakmaAd;
//    }

    public HomeDto(int gonderiId, int kullaniciId, String gonderiIcerigi, int begeniSayisi,
                   LocalDate gonderiTarihi, String takipEdilenKullaniciTakmaAd, String kullaniciResim, boolean begenildiMi) {
        this.gonderiId = gonderiId;
        this.kullaniciId = kullaniciId;
        this.gonderiIcerigi = gonderiIcerigi;
        this.begeniSayisi = begeniSayisi;
        this.gonderiTarihi = gonderiTarihi;
        this.takipEdilenKullaniciTakmaAd = takipEdilenKullaniciTakmaAd;
        this.begenildiMi = begenildiMi;
        this.kullaniciResim = kullaniciResim;
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

    public LocalDate getGonderiTarihi() {
        return gonderiTarihi;
    }

    public void setGonderiTarihi(LocalDate gonderiTarihi) {
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
}
