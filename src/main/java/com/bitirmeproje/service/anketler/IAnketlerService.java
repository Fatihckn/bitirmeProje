package com.bitirmeproje.service.anketler;

import com.bitirmeproje.dto.anketler.AnketOneriDto;
import com.bitirmeproje.dto.anketler.AnketlerSaveDto;
import com.bitirmeproje.dto.anketler.GirisYapanKullaniciAnketDto;
import com.bitirmeproje.dto.anketler.KullaniciCevapladigiAnketlerDto;
import com.bitirmeproje.model.Anketler;

import java.util.List;

public interface IAnketlerService {

    Anketler anketlerSave(AnketlerSaveDto anketlerDto);

    List<GirisYapanKullaniciAnketDto> getKullaniciAnketler();

    void deleteAnket(int anketId);

    List<AnketOneriDto> kullaniciAnketOneri();

    List<KullaniciCevapladigiAnketlerDto> getKullaniciCevapladigiAnketler();
}
