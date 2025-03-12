package com.bitirmeproje.dto.gonderiler;

import java.time.LocalDate;

public class GonderiResponseDto {
    private int gonderiId;
    private String gonderiIcerigi;
    private LocalDate gonderiTarihi;
    private int gonderiBegeniSayisi;
    private String kullaniciTakmaAd;

    public GonderiResponseDto() {}

    public GonderiResponseDto(Number gonderiId, String gonderiIcerigi, LocalDate gonderiTarihi,
                              Number gonderiBegeniSayisi, String kullaniciTakmaAd) {
        this.gonderiId = gonderiId.intValue();  // Number → int dönüşümü
        this.gonderiIcerigi = gonderiIcerigi;
        this.gonderiTarihi = gonderiTarihi;
        this.gonderiBegeniSayisi = gonderiBegeniSayisi != null ? gonderiBegeniSayisi.intValue() : 0;
        this.kullaniciTakmaAd = kullaniciTakmaAd != null ? kullaniciTakmaAd : "Bilinmeyen Kullanıcı";
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

    public LocalDate getGonderiTarihi() {
        return gonderiTarihi;
    }

    public void setGonderiTarihi(LocalDate gonderiTarihi) {
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
