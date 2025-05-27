package com.bitirmeproje.helper.dto.gonderi;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Gonderiler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GonderiConverter implements IEntityDtoConverter<Gonderiler, GonderiDto> {
    private final GetUserByToken getUserByToken;

    public GonderiConverter(GetUserByToken getUserByToken) {
        this.getUserByToken = getUserByToken;
    }

    @Override
    public GonderiDto convertToDTO(Gonderiler gonderiler) {
        return new GonderiDto(
                gonderiler.getGonderiId(),
                gonderiler.getGonderiIcerigi(),
                gonderiler.getGonderiTarihi(),
                gonderiler.getGonderiBegeniSayisi(),
                gonderiler.getKullaniciId().getKullaniciTakmaAd()
        );
    }

    @Override
    public Gonderiler convertToEntity(GonderiDto gonderiDto) {
        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiIcerigi(gonderiDto.getGonderiIcerigi());
        gonderi.setGonderiBegeniSayisi(0);
        gonderi.setGonderiTarihi(LocalDateTime.now());
        gonderi.setKullaniciId(getUserByToken.getUser());
        return gonderi;
    }
}
