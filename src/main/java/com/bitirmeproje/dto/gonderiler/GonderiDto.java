package com.bitirmeproje.dto.gonderiler;

import java.time.LocalDateTime;

public class GonderiDto {
    private int gonderiId;
    private String gonderiIcerigi;
    private LocalDateTime gonderiTarihi;
    private int gonderiBegeniSayisi;
    private String kullaniciTakmaAd;

    public GonderiDto() {}

    public GonderiDto(int gonderiId, String gonderiIcerigi, LocalDateTime gonderiTarihi,
                      Number gonderiBegeniSayisi, String kullaniciTakmaAd) {
        this.gonderiId = gonderiId; // Number → int dönüşümü
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
}
