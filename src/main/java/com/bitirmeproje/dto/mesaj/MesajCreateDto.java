package com.bitirmeproje.dto.mesaj;

public class MesajCreateDto {

    private int mesajGonderenKullaniciId;

    private int mesajGonderilenKullaniciId;

    private String mesajIcerigi;

    public MesajCreateDto() {
    }

    public MesajCreateDto(int mesajGonderenKullaniciId, int mesajGonderilenKullaniciId, String mesajIcerigi) {
        this.mesajGonderenKullaniciId = mesajGonderenKullaniciId;
        this.mesajGonderilenKullaniciId = mesajGonderilenKullaniciId;
        this.mesajIcerigi = mesajIcerigi;
    }

    public int getMesajGonderenKullaniciId() {
        return mesajGonderenKullaniciId;
    }

    public int getMesajGonderilenKullaniciId() {
        return mesajGonderilenKullaniciId;
    }

    public String getMesajIcerigi() {
        return mesajIcerigi;
    }
}
