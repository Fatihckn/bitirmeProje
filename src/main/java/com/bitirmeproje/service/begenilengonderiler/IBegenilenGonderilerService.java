package com.bitirmeproje.service.begenilengonderiler;

import com.bitirmeproje.dto.begenilengonderiler.BegenilenGonderilerDto;
import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderilerAllDto;
import com.bitirmeproje.model.Gonderiler;

import java.util.List;

public interface IBegenilenGonderilerService {

    void begeniEkle(int gonderiId);

    void begeniKaldir(int gonderiId);

    int gonderiBegeniSayisi(int gonderiId);

    List<BegenilenGonderilerDto> kullanicininBegenileri();

    List<GonderilerAllDto> gonderiBegeniSayisiAll();

}
