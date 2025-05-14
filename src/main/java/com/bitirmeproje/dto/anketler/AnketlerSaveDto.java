package com.bitirmeproje.dto.anketler;

import java.util.List;

public class AnketlerSaveDto {
    private String soruYazisi;

    private List<String> soruSecenekleri;

    public List<String> getSoruSecenekleri() {
        return soruSecenekleri;
    }

    public void setSoruSecenekleri(List<String> soruSecenekleri) {
        this.soruSecenekleri = soruSecenekleri;
    }

    public String getSoruYazisi() {
        return soruYazisi;
    }

    public void setSoruYazisi(String soruYazisi) {
        this.soruYazisi = soruYazisi;
    }
}
