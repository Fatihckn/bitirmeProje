package com.bitirmeproje.dto.user;

import com.bitirmeproje.model.Role;
import java.time.LocalDate;

public class UserAllDto extends BaseUserDto {
    private Role kullaniciRol;

    public UserAllDto() {}

    public UserAllDto(int kullaniciId, String kullaniciTakmaAd, String ePosta, String kullaniciBio,
                      String kullaniciProfilResmi, String kullaniciTelefonNo, LocalDate kullaniciDogumTarihi,
                      LocalDate kullaniciUyeOlmaTarihi, Role kullaniciRol) {
        super(kullaniciId, kullaniciTakmaAd, ePosta, kullaniciBio, kullaniciProfilResmi, kullaniciTelefonNo, kullaniciDogumTarihi, kullaniciUyeOlmaTarihi);
        this.kullaniciRol = kullaniciRol;
    }

    public Role getKullaniciRol() {
        return kullaniciRol;
    }

    public void setKullaniciRol(Role kullaniciRol) {
        this.kullaniciRol = kullaniciRol;
    }

}
