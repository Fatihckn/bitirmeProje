package com.bitirmeproje.helper.dto;

import com.bitirmeproje.dto.user.UserDto;
import com.bitirmeproje.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverterer implements IEntityDtoConverter<User,UserDto> {

    @Override
    public UserDto convertToDTO(User user) {
        return new UserDto(
                user.getKullaniciId(),
                user.getKullaniciTakmaAd(),
                user.getePosta(),
                user.getKullaniciBio(),
                user.getKullaniciProfilResmi() != null ? user.getKullaniciProfilResmi() : null,
                user.getKullaniciTelefonNo(),
                user.getKullaniciDogumTarihi(),
                user.getKullaniciUyeOlmaTarihi()
        );
    }
}
