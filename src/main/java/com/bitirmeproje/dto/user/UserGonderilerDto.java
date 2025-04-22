package com.bitirmeproje.dto.user;

import com.bitirmeproje.dto.gonderiler.GonderiDto;

import java.util.List;

public class UserGonderilerDto extends BaseUserDto{
    private List<GonderiDto> gonderiler;

    private int takipcisiKisiSayisi;

    private int takipEttigiKisiSayisi;

    public UserGonderilerDto() {}

    public UserGonderilerDto(List<GonderiDto> gonderiler, int takipcisiKisiSayisi,
                             Integer takipEttigiKisiSayisi) {
        super();
        this.gonderiler = gonderiler;
        this.takipcisiKisiSayisi = takipcisiKisiSayisi;
        this.takipEttigiKisiSayisi = takipEttigiKisiSayisi;
    }

    public List<GonderiDto> getGonderiler() {
        return gonderiler;
    }

    public void setGonderiler(List<GonderiDto> gonderiler) {
        this.gonderiler = gonderiler;
    }

    public int getTakipcisiKisiSayisi() {
        return takipcisiKisiSayisi;
    }

    public void setTakipcisiKisiSayisi(int takipcisiKisiSayisi) {
        this.takipcisiKisiSayisi = takipcisiKisiSayisi;
    }

    public int getTakipEttigiKisiSayisi() {
        return takipEttigiKisiSayisi;
    }

    public void setTakipEttigiKisiSayisi(int takipEttigiKisiSayisi) {
        this.takipEttigiKisiSayisi = takipEttigiKisiSayisi;
    }
}
