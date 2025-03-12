package com.bitirmeproje.model;

import jakarta.persistence.*;

@Entity
@Table(name = "yorum_takip")
public class YorumTakip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "yorum_takip_id")
    private int yorumTakipId;

    @ManyToOne
    @JoinColumn(name = "yeni_yorum_id")
    private YeniYorum yeniYorumId;

    public int getYorumTakipId() {
        return yorumTakipId;
    }

    public void setYorumTakipId(int yorumTakipId) {
        this.yorumTakipId = yorumTakipId;
    }

    public YeniYorum getYorumYorumId() {
        return yeniYorumId;
    }

    public void setYorumYorumId(YeniYorum yorumYorumId) {
        this.yeniYorumId = yorumYorumId;
    }

    public YeniYorum getYeniYorumId() {
        return yeniYorumId;
    }

    public void setYeniYorumId(YeniYorum yeniYorumId) {
        this.yeniYorumId = yeniYorumId;
    }
}
