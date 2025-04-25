package com.bitirmeproje.service.aramagecmisi;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;

import java.util.List;

public interface IAramaGecmisiService {

    void AramaKaydet(AramaGecmisiDto aramaGecmisiDto);

    List<AramaGecmisiDto> getKullaniciAramaGecmisi();

    void deleteArama(int aramaGecmisiId);
}
