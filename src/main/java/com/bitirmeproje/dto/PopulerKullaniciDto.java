package com.bitirmeproje.dto;

public class PopulerKullaniciDto {
    private int kullaniciId;
    private String kullaniciTakmaAd;
    private long takipciSayisi;

    public PopulerKullaniciDto(int kullaniciId, String kullaniciTakmaAd, long takipciSayisi) {
        this.kullaniciId = kullaniciId;
        this.kullaniciTakmaAd = kullaniciTakmaAd;
        this.takipciSayisi = takipciSayisi;
    }

    public int getKullaniciId() {
        return kullaniciId;
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

    public long getTakipciSayisi() {
        return takipciSayisi;
    }

    public void setTakipciSayisi(long takipciSayisi) {
        this.takipciSayisi = takipciSayisi;
    }
}
