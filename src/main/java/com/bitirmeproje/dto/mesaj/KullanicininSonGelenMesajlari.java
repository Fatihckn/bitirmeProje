package com.bitirmeproje.dto.mesaj;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class KullanicininSonGelenMesajlari {
    int mesajId;

    String mesajIcerigi;

    LocalDateTime mesajGonderilmeZamani;

    String karsiTarafAdi;

    String karsiTarafProfilResmi;

    int karsiTarafId;

    public KullanicininSonGelenMesajlari(int mesajId, String mesajIcerigi, LocalDateTime mesajGonderilmeZamani, String karsiTarafAdi, String karsiTarafProfilResmi, int karsiTarafId) {
        this.mesajId = mesajId;
        this.mesajIcerigi = mesajIcerigi;
        this.mesajGonderilmeZamani = mesajGonderilmeZamani;
        this.karsiTarafAdi = karsiTarafAdi;
        this.karsiTarafProfilResmi = karsiTarafProfilResmi;
        this.karsiTarafId = karsiTarafId;
    }

    public int getMesajId() {
        return mesajId;
    }

    public void setMesajId(int mesajId) {
        this.mesajId = mesajId;
    }

    public String getMesajIcerigi() {
        return mesajIcerigi;
    }

    public void setMesajIcerigi(String mesajIcerigi) {
        this.mesajIcerigi = mesajIcerigi;
    }

    public LocalDateTime getMesajGonderilmeZamani() {
        return mesajGonderilmeZamani;
    }

    public void setMesajGonderilmeZamani(LocalDateTime mesajGonderilmeZamani) {
        this.mesajGonderilmeZamani = mesajGonderilmeZamani;
    }

    public String getKarsiTarafAdi() {
        return karsiTarafAdi;
    }

    public void setKarsiTarafAdi(String karsiTarafAdi) {
        this.karsiTarafAdi = karsiTarafAdi;
    }

    public String getKarsiTarafProfilResmi() {
        return karsiTarafProfilResmi;
    }

    public void setKarsiTarafProfilResmi(String karsiTarafProfilResmi) {
        this.karsiTarafProfilResmi = karsiTarafProfilResmi;
    }

    public int getKarsiTarafId() {
        return karsiTarafId;
    }

    public void setKarsiTarafId(int karsiTarafId) {
        this.karsiTarafId = karsiTarafId;
    }
}
