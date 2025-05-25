package com.bitirmeproje.dto.anketler;

import java.time.LocalDateTime;


public class AnketOneriDto extends GirisYapanKullaniciAnketDto{
    private Boolean kullaniciCevapVarMi;

    public AnketOneriDto() {}

    public AnketOneriDto(int anketId, String anketSorusu,
                         LocalDateTime anketOlusturulmaTarihi, Boolean kullaniciCevapVarMi) {
        super(anketId, anketSorusu, anketOlusturulmaTarihi);
        this.kullaniciCevapVarMi = kullaniciCevapVarMi;
    }

    public Boolean getKullaniciCevapVarMi() {
        return kullaniciCevapVarMi;
    }

    public void setKullaniciCevapVarMi(Boolean kullaniciCevapVarMi) {
        this.kullaniciCevapVarMi = kullaniciCevapVarMi;
    }
}
