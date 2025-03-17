package com.bitirmeproje.service.aramagecmisi;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;

import java.time.LocalDate;
import java.util.List;

public interface IAramaGecmisiService {

    void AramaKaydet(AramaGecmisiDto aramaGecmisiDto);

    List<AramaGecmisiDto> getKullaniciAramaGecmisi();

    List<AramaGecmisiDto> getKullaniciAramaGecmisiByDate(LocalDate baslangic, LocalDate bitis);

    void deleteArama(int aramaGecmisiId);

    List<Object[]> getPopulerAramalar();
}
