package com.bitirmeproje.dto.mesaj;

import com.bitirmeproje.model.Mesaj;

import java.time.LocalDate;

public class MesajDto {
    private int mesajId;
    private int gonderenKullaniciId;
    private int aliciKullaniciId;
    private String mesajIcerigi;
    private LocalDate mesajGonderilmeZamani;

    // Constructor
    public MesajDto(Mesaj mesaj) {
        this.mesajId = mesaj.getMesajId();
        this.gonderenKullaniciId = mesaj.getMesajGonderenKullaniciId().getKullaniciId();
        this.aliciKullaniciId = mesaj.getMesajGonderilenKullaniciId().getKullaniciId();
        this.mesajIcerigi = mesaj.getMesajIcerigi();
        this.mesajGonderilmeZamani = mesaj.getMesajGonderilmeZamani();
    }

    // Getter ve Setter metodları
    public int getMesajId() {
        return mesajId;
    }

    public void setMesajId(int mesajId) {
        this.mesajId = mesajId;
    }

    public int getGonderenKullaniciId() {
        return gonderenKullaniciId;
    }

    public void setGonderenKullaniciId(int gonderenKullaniciId) {
        this.gonderenKullaniciId = gonderenKullaniciId;
    }

    public int getAliciKullaniciId() {
        return aliciKullaniciId;
    }

    public void setAliciKullaniciId(int aliciKullaniciId) {
        this.aliciKullaniciId = aliciKullaniciId;
    }

    public String getMesajIcerigi() {
        return mesajIcerigi;
    }

    public void setMesajIcerigi(String mesajIcerigi) {
        this.mesajIcerigi = mesajIcerigi;
    }

    public LocalDate getMesajGonderilmeZamani() {
        return mesajGonderilmeZamani;
    }

    public void setMesajGonderilmeZamani(LocalDate mesajGonderilmeZamani) {
        this.mesajGonderilmeZamani = mesajGonderilmeZamani;
    }

}
