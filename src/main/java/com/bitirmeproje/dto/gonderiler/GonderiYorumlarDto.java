package com.bitirmeproje.dto.gonderiler;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDtoWithBegenildiMi;

import java.time.LocalDateTime;
import java.util.List;

public class GonderiYorumlarDto extends GonderiDto{
    private List<YeniYorumDtoWithBegenildiMi> yorumlar;
    private String kullaniciFoto;
    private String gonderiAtanKullaniciFoto;
    private int kullaniciId;

    public GonderiYorumlarDto() {}

    public GonderiYorumlarDto(int gonderiId, String gonderiIcerigi, LocalDateTime gonderiTarihi,
                              Number gonderiBegeniSayisi, String kullaniciTakmaAd, String gonderiMedyaUrl, Boolean begenildiMi,
                              String kullaniciFoto, String gonderiAtanKullaniciFoto, List<YeniYorumDtoWithBegenildiMi> yorumlar) {
        super(gonderiId, gonderiIcerigi, gonderiTarihi, gonderiBegeniSayisi, kullaniciTakmaAd, gonderiMedyaUrl, begenildiMi);
        this.yorumlar = yorumlar;
        this.kullaniciFoto = kullaniciFoto;
        this.gonderiAtanKullaniciFoto = gonderiAtanKullaniciFoto;
    }

    public List<YeniYorumDtoWithBegenildiMi> getYorumlar() {
        return yorumlar;
    }

    public void setYorumlar(List<YeniYorumDtoWithBegenildiMi> yorumlar) {
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

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }
}
