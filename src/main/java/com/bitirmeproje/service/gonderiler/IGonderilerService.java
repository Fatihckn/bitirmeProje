package com.bitirmeproje.service.gonderiler;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderiEkleDto;
import com.bitirmeproje.dto.gonderiler.YapayZekaResimEkleDto;

import java.util.List;

public interface IGonderilerService {

    List<GonderiDto> kullaniciGonderileriniGetir();

    void yeniGonderiEkle(GonderiEkleDto gonderiEkleDto);

    List<GonderiDto> populerGonderileriGetir();

    void gonderiSil(int gonderiId);

    GonderiDto getArananGonderi(int gonderiId);

    void yapayZekaResimOlusturma(YapayZekaResimEkleDto gonderiEkle);
}
