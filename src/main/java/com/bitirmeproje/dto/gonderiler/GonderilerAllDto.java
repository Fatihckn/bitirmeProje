package com.bitirmeproje.dto.gonderiler;

public class GonderilerAllDto {
    private int gonderi_id;
    private int gonderi_begeni_sayisi;

    public GonderilerAllDto(int gonderi_id, int gonderi_begeni_sayisi) {
        this.gonderi_id = gonderi_id;
        this.gonderi_begeni_sayisi = gonderi_begeni_sayisi;
    }

    public int getGonderi_id() {
        return gonderi_id;
    }

    public void setGonderi_id(int gonderi_id) {
        this.gonderi_id = gonderi_id;
    }

    public int getGonderi_begeni_sayisi() {
        return gonderi_begeni_sayisi;
    }

    public void setGonderi_begeni_sayisi(int gonderi_begeni_sayisi) {
        this.gonderi_begeni_sayisi = gonderi_begeni_sayisi;
    }
}
