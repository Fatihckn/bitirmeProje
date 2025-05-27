package com.bitirmeproje.helper.dto.gonderi;

import com.bitirmeproje.dto.gonderiler.YapayZekaResimEkleDto;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.MedyaTuru;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GonderiConverterYapayZekaResimEkleDto implements IEntityDtoConverter<Gonderiler, YapayZekaResimEkleDto> {
    private final GetUserByToken getUserByToken;

    public GonderiConverterYapayZekaResimEkleDto(GetUserByToken getUserByToken) {
        this.getUserByToken = getUserByToken;
    }

    @Override
    public Gonderiler convertToEntity(YapayZekaResimEkleDto gonderiDto) {
        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiIcerigi(gonderiDto.getGonderiIcerigi());
        gonderi.setGonderiBegeniSayisi(0);
        gonderi.setGonderiTarihi(LocalDateTime.now());
        gonderi.setKullaniciId(getUserByToken.getUser());
        gonderi.setGonderiMedyaUrl(gonderiDto.getResimUrl());
        gonderi.setGonderiMedyaTuru(MedyaTuru.FOTO);
        return gonderi;
    }
}
