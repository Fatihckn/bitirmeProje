package com.bitirmeproje.dto.mesaj;

import com.bitirmeproje.model.Mesaj;

public class MesajSohbetGecmisiGetirDto extends BaseMesajDto {
    private String mesajGonderilenKullaniciResmi;

    private String mesajGonderilenKullaniciAdi;

    private String mesajGonderenKullaniciAdi;

    private String mesajGonderenKullaniciResmi;

    public MesajSohbetGecmisiGetirDto(Mesaj mesaj, String mesajGonderilenKullaniciAdi, String mesajGonderilenKullaniciId, String mesajGonderenKullaniciAdi,
                                      String mesajGonderenKullaniciResmi) {
        super(mesaj);
        this.mesajGonderilenKullaniciAdi = mesajGonderilenKullaniciAdi;
        this.mesajGonderilenKullaniciResmi = mesajGonderilenKullaniciId;
        this.mesajGonderenKullaniciAdi = mesajGonderenKullaniciAdi;
        this.mesajGonderenKullaniciResmi = mesajGonderenKullaniciResmi;
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

    public String getMesajGonderenKullaniciResmi() {
        return mesajGonderenKullaniciResmi;
    }

    public void setMesajGonderenKullaniciResmi(String mesajGonderenKullaniciResmi) {
        this.mesajGonderenKullaniciResmi = mesajGonderenKullaniciResmi;
    }
}
