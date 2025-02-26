package com.bitirmeproje.dto;

import com.bitirmeproje.model.Role;
import java.time.LocalDate;

public class UserAllDto extends BaseUserDto {
    private int kullaniciId;
    private Role kullaniciRol;

    public UserAllDto() {}

    public UserAllDto(int kullaniciId, String kullaniciTakmaAd, String ePosta, String kullaniciBio,
                      String kullaniciProfilResmi, String kullaniciTelefonNo, LocalDate kullaniciDogumTarihi,
                      LocalDate kullaniciUyeOlmaTarihi, Role kullaniciRol) {
        super(kullaniciTakmaAd, ePosta, kullaniciBio, kullaniciProfilResmi, kullaniciTelefonNo, kullaniciDogumTarihi, kullaniciUyeOlmaTarihi);
        this.kullaniciId = kullaniciId;
        this.kullaniciRol = kullaniciRol;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public Role getKullaniciRol() {
        return kullaniciRol;
    }

    public void setKullaniciRol(Role kullaniciRol) {
        this.kullaniciRol = kullaniciRol;
    }

}
