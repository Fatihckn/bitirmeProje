package com.bitirmeproje.helper.dto;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Gonderiler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GonderiConvert implements IEntityDtoConvert<Gonderiler, GonderiDto>{
    private final GetUserByToken getUserByToken;

    public GonderiConvert(GetUserByToken getUserByToken) {
        this.getUserByToken = getUserByToken;
    }

    @Override
    public GonderiDto convertToDTO(Gonderiler gonderiler) {
        return new GonderiDto(
                gonderiler.getGonderiId(),
                gonderiler.getGonderiIcerigi(),
                gonderiler.getGonderiTarihi(),
                gonderiler.getGonderiBegeniSayisi()
        );
    }

    @Override
    public Gonderiler convertToEntity(GonderiDto gonderiDto) {
        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiIcerigi(gonderiDto.getGonderiIcerigi());
        gonderi.setGonderiBegeniSayisi(0);
        gonderi.setGonderiTarihi(LocalDate.now());
        gonderi.setKullaniciId(getUserByToken.getUser());
        return gonderi;
    }
}
