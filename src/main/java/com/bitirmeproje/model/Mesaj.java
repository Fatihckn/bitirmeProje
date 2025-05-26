package com.bitirmeproje.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mesaj")
public class Mesaj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mesaj_id")
    private int mesajId;

    @ManyToOne
    @JoinColumn(name = "mesaj_gonderen_kullanici_id")
    private User mesajGonderenKullaniciId;

    @ManyToOne
    @JoinColumn(name = "mesaj_gonderilen_kullanici_id")
    private User mesajGonderilenKullaniciId;

    @Column(name = "mesaj_icerigi")
    private String mesajIcerigi;

    @Column(name = "mesaj_gonderilme_zamani")
    private LocalDateTime mesajGonderilmeZamani;

    @Column(name = "mesaj_okundu_mu")
    private boolean mesajOkunduMu = false;

    @Column(name = "gonderen_sildi_mi")
    private boolean gonderenSildiMi = false;

    @Column(name = "alici_sildi_mi")
    private boolean aliciSildiMi = false;

    public int getMesajId() {
        return mesajId;
    }

    public void setMesajId(int mesajId) {
        this.mesajId = mesajId;
    }

    public User getMesajGonderenKullaniciId() {
        return mesajGonderenKullaniciId;
    }

    public void setMesajGonderenKullaniciId(User mesajGonderenKullaniciId) {
        this.mesajGonderenKullaniciId = mesajGonderenKullaniciId;
    }

    public User getMesajGonderilenKullaniciId() {
        return mesajGonderilenKullaniciId;
    }

    public void setMesajGonderilenKullaniciId(User mesajGonderilenKullaniciId) {
        this.mesajGonderilenKullaniciId = mesajGonderilenKullaniciId;
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

    public boolean isMesajOkunduMu() {
        return mesajOkunduMu;
    }

    public void setMesajOkunduMu(boolean mesajOkunduMu) {
        this.mesajOkunduMu = mesajOkunduMu;
    }

    public boolean isGonderenSildiMi() {
        return gonderenSildiMi;
    }

    public void setGonderenSildiMi(boolean gonderenSildiMi) {
        this.gonderenSildiMi = gonderenSildiMi;
    }

    public boolean isAliciSildiMi() {
        return aliciSildiMi;
    }

    public void setAliciSildiMi(boolean aliciSildiMi) {
        this.aliciSildiMi = aliciSildiMi;
    }
}
