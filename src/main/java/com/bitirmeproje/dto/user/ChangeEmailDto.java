package com.bitirmeproje.dto.user;

public class ChangeEmailDto {
    private String eskiEposta;
    private String sifre;
    private String yeniEposta;
    private String otp;

    public String getEskiEposta() {
        return eskiEposta;
    }

    public void setEskiEposta(String eskiEposta) {
        this.eskiEposta = eskiEposta;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getYeniEposta() {
        return yeniEposta;
    }

    public void setYeniEposta(String yeniEposta) {
        this.yeniEposta = yeniEposta;
    }

    public String getOtp() {return otp;}

    public void setOtp(String otp) {this.otp = otp;}
}
