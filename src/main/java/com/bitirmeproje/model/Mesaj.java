package com.bitirmeproje.model;


import jakarta.persistence.*;

import java.time.LocalDate;
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
}
