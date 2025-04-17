package com.bitirmeproje.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
public class Follows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follows_id",nullable = false)
    private int followsId;

    @ManyToOne
    @JoinColumn (name = "takip_eden_kullanici_id")
    private User takipEdenKullaniciId;

    @ManyToOne
    @JoinColumn (name = "takip_edilen_kullanici_id")
    private User takipEdilenKullaniciId;

    @Column (name = "takip_etme_tarihi")
    private LocalDateTime takipEtmeTarihi;

    public int getFollowsId() {
        return followsId;
    }

    public void setFollowsId(int followsId) {
        this.followsId = followsId;
    }

    public User getTakipEdenKullaniciId() {
        return takipEdenKullaniciId;
    }

    public void setTakipEdenKullaniciId(User takipEdenKullaniciId) {
        this.takipEdenKullaniciId = takipEdenKullaniciId;
    }

    public User getTakipEdilenKullaniciId() {
        return takipEdilenKullaniciId;
    }

    public void setTakipEdilenKullaniciId(User takipEdilenKullaniciId) {
        this.takipEdilenKullaniciId = takipEdilenKullaniciId;
    }

    public LocalDateTime getTakipEtmeTarihi() {
        return takipEtmeTarihi;
    }

    public void setTakipEtmeTarihi(LocalDateTime takipEtmeTarihi) {
        this.takipEtmeTarihi = takipEtmeTarihi;
    }
}
