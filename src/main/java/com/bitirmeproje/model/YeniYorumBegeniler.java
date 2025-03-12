package com.bitirmeproje.model;

import jakarta.persistence.*;

@Entity
@Table(name = "yeni_yorum_begeniler")
public class YeniYorumBegeniler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "begeni_id")
    private int begeniId;

    @ManyToOne
    @JoinColumn(name = "yeni_yorum_id", nullable = false)
    private YeniYorum yeniYorum;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    private User kullanici; // ❗ "kullaniciId" yerine "kullanici" olmalı

    // Getter ve Setter metotları
    public int getBegeniId() {
        return begeniId;
    }

    public void setBegeniId(int begeniId) {
        this.begeniId = begeniId;
    }

    public YeniYorum getYeniYorum() {
        return yeniYorum;
    }

    public void setYeniYorum(YeniYorum yeniYorum) {
        this.yeniYorum = yeniYorum;
    }

    public User getKullanici() { // ❗ Hata burada olabilir
        return kullanici;
    }

    public void setKullanici(User kullanici) {
        this.kullanici = kullanici;
    }
}