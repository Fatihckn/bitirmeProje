package com.bitirmeproje.dto.mesaj;

import com.bitirmeproje.model.Mesaj;

public class MesajAtmaDto extends BaseMesajDto {
    private String mesajAtanKullaniciFoto;

    private String mesajAtanKullaniciTakmaAdi;

    public MesajAtmaDto(){}

    public MesajAtmaDto(Mesaj mesaj, String mesajAtanKullaniciFoto, String mesajAtanKullaniciTakmaAdi){
        super(mesaj);
        this.mesajAtanKullaniciFoto = mesajAtanKullaniciFoto;
        this.mesajAtanKullaniciTakmaAdi = mesajAtanKullaniciTakmaAdi;
    }

    public String getMesajAtanKullaniciFoto() {
        return mesajAtanKullaniciFoto;
    }

    public void setMesajAtanKullaniciFoto(String mesajAtanKullaniciFoto) {
        this.mesajAtanKullaniciFoto = mesajAtanKullaniciFoto;
    }

    public String getMesajAtanKullaniciTakmaAdi() {
        return mesajAtanKullaniciTakmaAdi;
    }

    public void setMesajAtanKullaniciTakmaAdi(String mesajAtanKullaniciTakmaAdi) {
        this.mesajAtanKullaniciTakmaAdi = mesajAtanKullaniciTakmaAdi;
    }
}
