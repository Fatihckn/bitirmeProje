package com.bitirmeproje.service.gonderiler;

import com.bitirmeproje.dto.gonderiler.GonderiDto;

import java.util.List;

public interface IGonderilerService {

    List<GonderiDto> kullaniciGonderileriniGetir();

    void yeniGonderiEkle(GonderiDto gonderiDto);

    List<GonderiDto> populerGonderileriGetir();

    void gonderiSil(int gonderiId);

    void gonderiGuncelle(int gonderiId, String yeniIcerik);
}
