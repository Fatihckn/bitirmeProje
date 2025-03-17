package com.bitirmeproje.dto.gonderiler;

import java.time.LocalDate;

public class GonderiDto {
    private int gonderiId;
    private String gonderiIcerigi;
    private LocalDate gonderiTarihi;
    private int gonderiBegeniSayisi;

    public GonderiDto() {}

    public GonderiDto(int gonderiId, String gonderiIcerigi, LocalDate gonderiTarihi,
                      Number gonderiBegeniSayisi) {
        this.gonderiId = gonderiId; // Number → int dönüşümü
        this.gonderiIcerigi = gonderiIcerigi;
        this.gonderiTarihi = gonderiTarihi;
        this.gonderiBegeniSayisi = gonderiBegeniSayisi != null ? gonderiBegeniSayisi.intValue() : 0;
    }

    public GonderiDto(int gonderiId, Number gonderiBegeniSayisi) {
        this.gonderiId = gonderiId; // Number → int dönüşümü
        this.gonderiBegeniSayisi = gonderiBegeniSayisi != null ? gonderiBegeniSayisi.intValue() : 0;
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
}
