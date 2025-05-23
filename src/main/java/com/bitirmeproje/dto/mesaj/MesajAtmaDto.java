package com.bitirmeproje.dto.mesaj;

import com.bitirmeproje.model.Mesaj;

public class MesajAtmaDto extends BaseMesajDto {
    private String mesajAtanKullaniciFoto;

    private String mesajAtanKullaniciTakmaAdi;

    private String mesajAtilanKullaniciFoto;

    private String mesajAtilanKullaniciTakmaAdi;

    public MesajAtmaDto(){}

    public MesajAtmaDto(Mesaj mesaj, String mesajAtanKullaniciFoto, String mesajAtanKullaniciTakmaAdi,
                        String mesajAtilanKullaniciFoto, String mesajAtilanKullaniciTakmaAdi){
        super(mesaj);
        this.mesajAtanKullaniciFoto = mesajAtanKullaniciFoto;
        this.mesajAtanKullaniciTakmaAdi = mesajAtanKullaniciTakmaAdi;
        this.mesajAtilanKullaniciFoto = mesajAtilanKullaniciFoto;
        this.mesajAtilanKullaniciTakmaAdi = mesajAtilanKullaniciTakmaAdi;
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

    public String getMesajAtilanKullaniciFoto() {
        return mesajAtilanKullaniciFoto;
    }

    public void setMesajAtilanKullaniciFoto(String mesajAtilanKullaniciFoto) {
        this.mesajAtilanKullaniciFoto = mesajAtilanKullaniciFoto;
    }

    public String getMesajAtilanKullaniciTakmaAdi() {
        return mesajAtilanKullaniciTakmaAdi;
    }

    public void setMesajAtilanKullaniciTakmaAdi(String mesajAtilanKullaniciTakmaAdi) {
        this.mesajAtilanKullaniciTakmaAdi = mesajAtilanKullaniciTakmaAdi;
    }
}
