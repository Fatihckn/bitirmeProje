package com.bitirmeproje.dto.mesaj;

public class MesajCreateDto extends BaseMesajDto {

    private final int mesajGonderilenKullaniciId;

    public MesajCreateDto(int mesajGonderilenKullaniciId) {
        this.mesajGonderilenKullaniciId = mesajGonderilenKullaniciId;
    }

    public int getMesajGonderilenKullaniciId() {
        return mesajGonderilenKullaniciId;
    }
}
