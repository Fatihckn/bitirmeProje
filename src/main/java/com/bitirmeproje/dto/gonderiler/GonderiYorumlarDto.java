package com.bitirmeproje.dto.gonderiler;

import com.bitirmeproje.model.YeniYorum;

import java.time.LocalDateTime;
import java.util.List;

public class GonderiYorumlarDto extends GonderiDto{
    private List<YeniYorum> yorumlar;
    private String kullaniciFoto;
    private String gonderiAtanKullaniciFoto;

    public GonderiYorumlarDto() {}

    public GonderiYorumlarDto(int gonderiId, String gonderiIcerigi, LocalDateTime gonderiTarihi,
                              Number gonderiBegeniSayisi, String kullaniciTakmaAd, String gonderiMedyaUrl, Boolean begenildiMi,
                              String kullaniciFoto, String gonderiAtanKullaniciFoto, List<YeniYorum> yorumlar) {
        super(gonderiId, gonderiIcerigi, gonderiTarihi, gonderiBegeniSayisi, kullaniciTakmaAd, gonderiMedyaUrl, begenildiMi);
        this.yorumlar = yorumlar;
        this.kullaniciFoto = kullaniciFoto;
        this.gonderiAtanKullaniciFoto = gonderiAtanKullaniciFoto;
    }

    public List<YeniYorum> getYorumlar() {
        return yorumlar;
    }

    public void setYorumlar(List<YeniYorum> yorumlar) {
        this.yorumlar = yorumlar;
    }

    public String getKullaniciFoto() {
        return kullaniciFoto;
    }

    public void setKullaniciFoto(String kullaniciFoto) {
        this.kullaniciFoto = kullaniciFoto;
    }

    public String getGonderiAtanKullaniciFoto() {
        return gonderiAtanKullaniciFoto;
    }

    public void setGonderiAtanKullaniciFoto(String gonderiAtanKullaniciFoto) {
        this.gonderiAtanKullaniciFoto = gonderiAtanKullaniciFoto;
    }
}
