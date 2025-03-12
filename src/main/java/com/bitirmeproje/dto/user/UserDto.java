package com.bitirmeproje.dto.user;

import java.time.LocalDate;

// Soyut sınıfların nesnesi oluşturulamadığından bu sınıfı kullanıyoruz.

public class UserDto extends BaseUserDto {
    public UserDto() {}

    public UserDto(String kullaniciTakmaAd, String ePosta, String kullaniciBio, String kullaniciProfilResmi,
                   String kullaniciTelefonNo, LocalDate kullaniciDogumTarihi, LocalDate kullaniciUyeOlmaTarihi) {
        super(kullaniciTakmaAd, ePosta, kullaniciBio, kullaniciProfilResmi, kullaniciTelefonNo, kullaniciDogumTarihi, kullaniciUyeOlmaTarihi);
    }
}
