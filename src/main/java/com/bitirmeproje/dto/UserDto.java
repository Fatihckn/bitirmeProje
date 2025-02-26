package com.bitirmeproje.dto;

import java.time.LocalDate;

public class UserDto extends BaseUserDto {
    public UserDto() {}

    public UserDto(String kullaniciTakmaAd, String ePosta, String kullaniciBio, String kullaniciProfilResmi,
                   String kullaniciTelefonNo, LocalDate kullaniciDogumTarihi, LocalDate kullaniciUyeOlmaTarihi) {
        super(kullaniciTakmaAd, ePosta, kullaniciBio, kullaniciProfilResmi, kullaniciTelefonNo, kullaniciDogumTarihi, kullaniciUyeOlmaTarihi);
    }
}
