package com.bitirmeproje.dto;

public class UserUpdateDto extends BaseUserDto {
    public UserUpdateDto() {
        super();
    }

    public UserUpdateDto(String kullaniciTakmaAd, String kullaniciBio, String kullaniciTelefonNo, String kullaniciProfilResmi) {
        this.kullaniciTakmaAd = kullaniciTakmaAd;
        this.kullaniciBio = kullaniciBio;
        this.kullaniciTelefonNo = kullaniciTelefonNo;
        this.kullaniciProfilResmi = kullaniciProfilResmi;
    }
}

