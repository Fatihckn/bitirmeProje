package com.bitirmeproje.dto.yeniyorum;

import java.time.LocalDateTime;
import java.util.List;

public class YeniYorumDtoWithBegenildiMi {
    private int yorumId;
    private String yeniYorumIcerigi;
    private LocalDateTime yeniYorumOlusturulmaTarihi;
    private int yeniYorumBegeniSayisi;
    private List<YeniYorumDtoWithBegenildiMi> altYorumlar;
    private Boolean yorumuBegendimMi;
    private String yorumYapanTakmaAd;
    private String yorumYapanResim;

    public YeniYorumDtoWithBegenildiMi() {}

    public YeniYorumDtoWithBegenildiMi(int yorumId, String yeniYorumIcerigi, LocalDateTime yeniYorumOlusturulmaTarihi,
                                       int yeniYorumBegeniSayisi, Boolean yorumuBegendimMi, String yorumYapanResim,
                                       String yorumYapanTakmaAd) {
        this.yorumId = yorumId;
        this.yeniYorumIcerigi = yeniYorumIcerigi;
        this.yeniYorumOlusturulmaTarihi = yeniYorumOlusturulmaTarihi;
        this.yeniYorumBegeniSayisi = yeniYorumBegeniSayisi;
        this.yorumuBegendimMi = yorumuBegendimMi;
        this.yorumYapanResim = yorumYapanResim;
        this.yorumYapanTakmaAd = yorumYapanTakmaAd;
    }

    public int getYorumId() {
        return yorumId;
    }

    public void setYorumId(int yorumId) {
        this.yorumId = yorumId;
    }

    public String getYeniYorumIcerigi() {
        return yeniYorumIcerigi;
    }

    public void setYeniYorumIcerigi(String yeniYorumIcerigi) {
        this.yeniYorumIcerigi = yeniYorumIcerigi;
    }

    public LocalDateTime getYeniYorumOlusturulmaTarihi() {
        return yeniYorumOlusturulmaTarihi;
    }

    public void setYeniYorumOlusturulmaTarihi(LocalDateTime yeniYorumOlusturulmaTarihi) {
        this.yeniYorumOlusturulmaTarihi = yeniYorumOlusturulmaTarihi;
    }

    public int getYeniYorumBegeniSayisi() {
        return yeniYorumBegeniSayisi;
    }

    public void setYeniYorumBegeniSayisi(int yeniYorumBegeniSayisi) {
        this.yeniYorumBegeniSayisi = yeniYorumBegeniSayisi;
    }

    public List<YeniYorumDtoWithBegenildiMi> getAltYorumlar() {
        return altYorumlar;
    }

    public void setAltYorumlar(List<YeniYorumDtoWithBegenildiMi> altYorumlar) {
        this.altYorumlar = altYorumlar;
    }

    public Boolean getYorumuBegendimMi() {
        return yorumuBegendimMi;
    }

    public void setYorumuBegendimMi(Boolean yorumuBegendimMi) {
        this.yorumuBegendimMi = yorumuBegendimMi;
    }

    public String getYorumYapanTakmaAd() {
        return yorumYapanTakmaAd;
    }

    public void setYorumYapanTakmaAd(String yorumYapanTakmaAd) {
        this.yorumYapanTakmaAd = yorumYapanTakmaAd;
    }

    public String getYorumYapanResim() {
        return yorumYapanResim;
    }

    public void setYorumYapanResim(String yorumYapanResim) {
        this.yorumYapanResim = yorumYapanResim;
    }
}
