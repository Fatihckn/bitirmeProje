package com.bitirmeproje.dto;

import lombok.*;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
public class LoginRequest {
    private String kullaniciEPosta;
    private String kullaniciSifre;

    public String getKullaniciEPosta() {
        return kullaniciEPosta;
    }
    public void setKullaniciEPosta(String kullaniciEPosta) {
        this.kullaniciEPosta = kullaniciEPosta;
    }
    public String getKullaniciSifre() {
        return kullaniciSifre;
    }
    public void setKullaniciSifre(String kullaniciSifre) {
        this.kullaniciSifre = kullaniciSifre;
    }
//    public static void main(String [] args) {  LoginRequest request = new LoginRequest();
//        request.setKullaniciEPosta("Kullanici");}


}
