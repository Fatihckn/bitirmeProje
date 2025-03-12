package com.bitirmeproje.dto.gonderiler;



public class GonderiCreateDto {

    private int kullaniciId;

    private String gonderiIcerigi;

    public GonderiCreateDto() {}

    public GonderiCreateDto(int kullaniciId, String gonderiIcerigi) {
        this.kullaniciId = kullaniciId;
        this.gonderiIcerigi = gonderiIcerigi;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getGonderiIcerigi() {
        return gonderiIcerigi;
    }

    public void setGonderiIcerigi(String gonderiIcerigi) {
        this.gonderiIcerigi = gonderiIcerigi;
    }
}
