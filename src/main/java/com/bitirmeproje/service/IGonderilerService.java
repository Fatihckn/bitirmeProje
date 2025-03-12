package com.bitirmeproje.service;

import com.bitirmeproje.dto.gonderiler.GonderiCreateDto;
import com.bitirmeproje.dto.gonderiler.GonderiUpdateDto;
import com.bitirmeproje.dto.gonderiler.GonderiResponseDto;

import java.util.List;

public interface IGonderilerService {

    List<GonderiResponseDto> kullanicininGonderileriniGetir(int kullaniciId);

    GonderiResponseDto yeniGonderiEkle(GonderiCreateDto gonderiDto);

    void gonderiyiSil(int gonderiId);

    GonderiResponseDto gonderiyiGuncelle(int gonderiId, GonderiUpdateDto gonderiUpdateDto);

    void gonderiyeBegeniEkle(int gonderiId);

    void gonderidenBegeniCikar(int gonderiId);

    List<GonderiResponseDto> enPopulerGonderileriGetir();
}
