package com.bitirmeproje.dto.user;

public class SifreDegistirDto {

    private String eskiSifre;

    private String yeniSifre;

    public SifreDegistirDto() {}

    public SifreDegistirDto(String eskiSifre, String yeniSifre) {
        this.eskiSifre = eskiSifre;
        this.yeniSifre = yeniSifre;
    }

    public String getEskiSifre() {
        return eskiSifre;
    }

    public void setEskiSifre(String eskiSifre) {
        this.eskiSifre = eskiSifre;
    }

    public String getYeniSifre() {
        return yeniSifre;
    }

    public void setYeniSifre(String yeniSifre) {
        this.yeniSifre = yeniSifre;
    }
}
