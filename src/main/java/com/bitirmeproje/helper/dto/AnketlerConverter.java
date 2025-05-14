package com.bitirmeproje.helper.dto;

import com.bitirmeproje.dto.anketler.AnketlerSaveDto;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Anketler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AnketlerConverter implements IEntityDtoConverter<Anketler, AnketlerSaveDto> {
    private final GetUserByToken getUserByToken;

    public AnketlerConverter(GetUserByToken getUserByToken) {
        this.getUserByToken = getUserByToken;
    }

    @Override
    public Anketler convertToEntity(AnketlerSaveDto dto) {
        Anketler anketler = new Anketler();
        anketler.setOlusturulmaTarihi(LocalDateTime.now());
        anketler.setAnketSorusu(dto.getSoruYazisi());
        anketler.setKullanici(getUserByToken.getUser());
        return anketler;
    }
}
