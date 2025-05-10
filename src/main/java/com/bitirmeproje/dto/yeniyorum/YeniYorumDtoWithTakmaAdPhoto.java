package com.bitirmeproje.dto.yeniyorum;

public class YeniYorumDtoWithTakmaAdPhoto {
    private int yeniYorumId;
    private int kullaniciId;
    private int gonderiId;
    private String yorumIcerigi;
    private Integer parentYorumId;
    private String kullaniciTakmaAd;
    private String kullaniciFoto;

    public YeniYorumDtoWithTakmaAdPhoto() {}

    public YeniYorumDtoWithTakmaAdPhoto(int yeniYorumId, int kullaniciId, int gonderiId, String yorumIcerigi, Integer parentYorumId,
                                        String kullaniciTakmaAd, String kullaniciFoto) {
        this.yeniYorumId = yeniYorumId;
        this.kullaniciId = kullaniciId;
        this.gonderiId = gonderiId;
        this.yorumIcerigi = yorumIcerigi;
        this.parentYorumId = parentYorumId;
        this.kullaniciTakmaAd = kullaniciTakmaAd;
        this.kullaniciFoto = kullaniciFoto;
    }

    public String getKullaniciTakmaAd() {
        return kullaniciTakmaAd;
    }

    public void setKullaniciTakmaAd(String kullaniciTakmaAd) {
        this.kullaniciTakmaAd = kullaniciTakmaAd;
    }

    public String getKullaniciFoto() {
        return kullaniciFoto;
    }

    public void setKullaniciFoto(String kullaniciFoto) {
        this.kullaniciFoto = kullaniciFoto;
    }

    public int getYeniYorumId() {
        return yeniYorumId;
    }

    public void setYeniYorumId(int yeniYorumId) {
        this.yeniYorumId = yeniYorumId;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public int getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(int gonderiId) {
        this.gonderiId = gonderiId;
    }

    public String getYorumIcerigi() {
        return yorumIcerigi;
    }

    public void setYorumIcerigi(String yorumIcerigi) {
        this.yorumIcerigi = yorumIcerigi;
    }

    public Integer getParentYorumId() {
        return parentYorumId;
    }

    public void setParentYorumId(Integer parentYorumId) {
        this.parentYorumId = parentYorumId;
    }
}
