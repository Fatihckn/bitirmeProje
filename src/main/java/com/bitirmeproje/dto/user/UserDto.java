package com.bitirmeproje.dto.user;

import java.time.LocalDate;

// Soyut sınıfların nesnesi oluşturulamadığından bu sınıfı kullanıyoruz.

public class UserDto extends BaseUserDto {
    public UserDto() {}

    public UserDto(int kullaniciId, String kullaniciTakmaAd, String ePosta, String kullaniciBio,
                   String kullaniciProfilResmi, String kullaniciTelefonNo, LocalDate kullaniciDogumTarihi,
                   LocalDate kullaniciUyeOlmaTarihi, String kullaniciCinsiyet, String kullaniciUyeUlkesi) {
        super(kullaniciId, kullaniciTakmaAd, ePosta, kullaniciBio, kullaniciProfilResmi,
                kullaniciTelefonNo, kullaniciDogumTarihi, kullaniciUyeOlmaTarihi, kullaniciCinsiyet,
                kullaniciUyeUlkesi);
    }
}
