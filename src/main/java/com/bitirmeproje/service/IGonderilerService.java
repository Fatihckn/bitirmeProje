package com.bitirmeproje.service;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderiResponseDto;
import com.bitirmeproje.model.Gonderiler;

import java.util.List;

public interface IGonderilerService {

    List<Gonderiler> kullaniciGonderileriniGetir(int kullaniciId);

    void begeniEkle(int gonderiId);

    void begeniKaldir(int gonderiId);

    void yeniGonderiEkle(int kullaniciId, GonderiDto gonderiDto);

    List<GonderiResponseDto> populerGonderileriGetir();

    void gonderiGuncelle(int gonderiId, String yeniIcerik);
}
