package com.bitirmeproje.dto.anketler;

import java.time.LocalDateTime;

public class KullaniciCevapladigiAnketlerDto extends GirisYapanKullaniciAnketDto{
    private int kullaniciCevapSecenekId;

    public KullaniciCevapladigiAnketlerDto() {}

    public KullaniciCevapladigiAnketlerDto(int anketId, String anketSorusu, LocalDateTime anketOlusturulmaTarihi,
                                           int kullaniciCevapSecenekId) {
        super(anketId, anketSorusu, anketOlusturulmaTarihi);
        this.kullaniciCevapSecenekId = kullaniciCevapSecenekId;
    }

    public int getKullaniciCevapSecenekId() {
        return kullaniciCevapSecenekId;
    }

    public void setKullaniciCevapSecenekId(int kullaniciCevapSecenekId) {
        this.kullaniciCevapSecenekId = kullaniciCevapSecenekId;
    }
}
