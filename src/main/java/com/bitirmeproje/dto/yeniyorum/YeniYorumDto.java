package com.bitirmeproje.dto.yeniyorum;

public class YeniYorumDto {
    protected int yeniYorumId;
    protected int kullaniciId;
    protected int gonderiId;
    protected String yorumIcerigi;
    protected Integer parentYorumId; // Alt yorumsa parent yorum ID

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

    public int getYeniYorumId() {return yeniYorumId;}

    public void setYeniYorumId(int yeniYorumId) {this.yeniYorumId = yeniYorumId;}
}