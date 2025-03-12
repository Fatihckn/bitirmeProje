package com.bitirmeproje.dto.yeniyorum;

public class YeniYorumDto {
    private int kullaniciId;
    private int gonderiId;
    private String yorumIcerigi;
    private Integer parentYorumId; // Alt yorumsa parent yorum ID

    // Getter ve Setter metodları
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