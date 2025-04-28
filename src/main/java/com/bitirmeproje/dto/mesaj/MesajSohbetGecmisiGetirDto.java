package com.bitirmeproje.dto.mesaj;

import com.bitirmeproje.model.Mesaj;

public class MesajSohbetGecmisiGetirDto extends BaseMesajDto {
    private String mesajGonderilenKullaniciResmi;

    private String mesajGonderilenKullaniciAdi;

    public MesajSohbetGecmisiGetirDto(Mesaj mesaj, String mesajGonderilenKullaniciAdi, String mesajGonderilenKullaniciId) {
        super(mesaj);
        this.mesajGonderilenKullaniciAdi = mesajGonderilenKullaniciAdi;
        this.mesajGonderilenKullaniciResmi = mesajGonderilenKullaniciId;
    }

    public String getMesajGonderilenKullaniciResmi() {
        return mesajGonderilenKullaniciResmi;
    }

    public void setMesajGonderilenKullaniciResmi(String mesajGonderilenKullaniciResmi) {
        this.mesajGonderilenKullaniciResmi = mesajGonderilenKullaniciResmi;
    }

    public String getMesajGonderilenKullaniciAdi() {
        return mesajGonderilenKullaniciAdi;
    }

    public void setMesajGonderilenKullaniciAdi(String mesajGonderilenKullaniciAdi) {
        this.mesajGonderilenKullaniciAdi = mesajGonderilenKullaniciAdi;
    }
}
