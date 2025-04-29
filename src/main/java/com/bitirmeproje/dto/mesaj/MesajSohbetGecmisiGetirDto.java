package com.bitirmeproje.dto.mesaj;

import com.bitirmeproje.model.Mesaj;

public class MesajSohbetGecmisiGetirDto extends BaseMesajDto {
    private String mesajGonderilenKullaniciResmi;

    private String mesajGonderilenKullaniciAdi;

    private String mesajGonderenKullaniciAdi;

    public MesajSohbetGecmisiGetirDto(Mesaj mesaj, String mesajGonderilenKullaniciAdi, String mesajGonderilenKullaniciId, String mesajGonderenKullaniciAdi) {
        super(mesaj);
        this.mesajGonderilenKullaniciAdi = mesajGonderilenKullaniciAdi;
        this.mesajGonderilenKullaniciResmi = mesajGonderilenKullaniciId;
        this.mesajGonderenKullaniciAdi = mesajGonderenKullaniciAdi;
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

    public String getMesajGonderenKullaniciAdi() {
        return mesajGonderenKullaniciAdi;
    }

    public void setMesajGonderenKullaniciAdi(String mesajGonderenKullaniciAdi) {
        this.mesajGonderenKullaniciAdi = mesajGonderenKullaniciAdi;
    }
}
