package com.bitirmeproje.dto;

import java.time.LocalDate;

public class FollowsDto {
    private int takipEdenKullaniciId;
    private int takipEdilenKullaniciId;
    private LocalDate takipEtmeTarihi;

    public FollowsDto(){}

    public FollowsDto(int takipEdenKullaniciId, int takipEdilenKullaniciId,LocalDate takipEtmeTarihi)
    {
        this.takipEdenKullaniciId=takipEdenKullaniciId;
        this.takipEdilenKullaniciId=takipEdilenKullaniciId;
        this.takipEtmeTarihi=takipEtmeTarihi;

    }

    public int getTakipEdenKullaniciId() {
        return takipEdenKullaniciId;
    }

    public void setTakipEdenKullaniciId(int takipEdenKullaniciId) {
        this.takipEdenKullaniciId = takipEdenKullaniciId;
    }

    public int getTakipEdilenKullaniciId() {
        return takipEdilenKullaniciId;
    }

    public void setTakipEdilenKullaniciId(int takipEdilenKullaniciId) {
        this.takipEdilenKullaniciId = takipEdilenKullaniciId;
    }

    public LocalDate getTakipEtmeTarihi() {
        return takipEtmeTarihi;
    }

    public void setTakipEtmeTarihi(LocalDate takipEtmeTarihi) {
        this.takipEtmeTarihi = takipEtmeTarihi;
    }
}
