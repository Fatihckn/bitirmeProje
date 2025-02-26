package com.bitirmeproje.dto;

public class LoginDto {

    private String ePosta;

    private String sifre;

    public LoginDto() {}

    public LoginDto(String ePosta, String sifre) {
        this.ePosta = ePosta;
        this.sifre = sifre;
    }

    public String getePosta() {
        return ePosta;
    }

    public void setePosta(String ePosta) {
        this.ePosta = ePosta;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}

// public record LoginDto(ePosta, sifre){}