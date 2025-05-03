package com.bitirmeproje.helper.dto;

import com.bitirmeproje.dto.user.UserAllDto;
import com.bitirmeproje.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserAllDtoConverter implements IEntityDtoConverter<User, UserAllDto> {

    @Override
    public UserAllDto convertToDTO(User user) {
        return new UserAllDto(
                user.getKullaniciId(),
                user.getKullaniciTakmaAd(),
                user.getePosta(),
                user.getKullaniciBio(),
                user.getKullaniciProfilResmi() != null ? user.getKullaniciProfilResmi() : null,
                user.getKullaniciTelefonNo(),
                user.getKullaniciDogumTarihi(),
                user.getKullaniciUyeOlmaTarihi(),
                user.getKullaniciRole(),
                user.getKullaniciCinsiyet(),
                user.getKullaniciUyeUlkesi()
        );
    }
}
