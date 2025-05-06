package com.bitirmeproje.dto.gonderiler;

import java.time.LocalDateTime;

public class GonderiDto {
    protected int gonderiId;
    protected String gonderiIcerigi;
    protected LocalDateTime gonderiTarihi;
    protected int gonderiBegeniSayisi;
    protected String kullaniciTakmaAd;
    protected Boolean begenildiMi;
    protected String gonderiMedyaUrl;
    protected long gonderiYorumSayisi;

    public GonderiDto() {}

    public GonderiDto(int gonderiId, String gonderiIcerigi, LocalDateTime gonderiTarihi,
                      Number gonderiBegeniSayisi, String kullaniciTakmaAd, String gonderiMedyaUrl, Boolean begenildiMi) {
        this.gonderiId = gonderiId;
        this.gonderiIcerigi = gonderiIcerigi;
        this.gonderiTarihi = gonderiTarihi;
        this.gonderiBegeniSayisi = gonderiBegeniSayisi != null ? gonderiBegeniSayisi.intValue() : 0;
        this.kullaniciTakmaAd = kullaniciTakmaAd;
        this.begenildiMi = begenildiMi;
        this.gonderiMedyaUrl = gonderiMedyaUrl;
    }

    public GonderiDto(int gonderiId, String gonderiIcerigi, LocalDateTime gonderiTarihi,
                      Number gonderiBegeniSayisi, String kullaniciTakmaAd, String gonderiMedyaUrl, Boolean begenildiMi,
                      long gonderiYorumSayisi) {
        this.gonderiId = gonderiId;
        this.gonderiIcerigi = gonderiIcerigi;
        this.gonderiTarihi = gonderiTarihi;
        this.gonderiBegeniSayisi = gonderiBegeniSayisi != null ? gonderiBegeniSayisi.intValue() : 0;
        this.kullaniciTakmaAd = kullaniciTakmaAd;
        this.begenildiMi = begenildiMi;
        this.gonderiMedyaUrl = gonderiMedyaUrl;
        this.gonderiYorumSayisi = gonderiYorumSayisi;
    }

    public GonderiDto(int gonderiId, String gonderiIcerigi, LocalDateTime gonderiTarihi,
                      Number gonderiBegeniSayisi, String kullaniciTakmaAd) {
        this.gonderiId = gonderiId;
        this.gonderiIcerigi = gonderiIcerigi;
        this.gonderiTarihi = gonderiTarihi;
        this.gonderiBegeniSayisi = gonderiBegeniSayisi != null ? gonderiBegeniSayisi.intValue() : 0;
        this.kullaniciTakmaAd = kullaniciTakmaAd;
    }

    // Getter ve Setter metotları...
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

    public String getKullaniciTakmaAd() {
        return kullaniciTakmaAd;
    }

    public void setKullaniciTakmaAd(String kullaniciTakmaAd) {
        this.kullaniciTakmaAd = kullaniciTakmaAd;
    }

    public Boolean getBegenildiMi() {
        return begenildiMi;
    }

    public void setBegenildiMi(Boolean begenildiMi) {
        this.begenildiMi = begenildiMi;
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
