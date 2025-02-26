package com.bitirmeproje.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProfilResmiGuncelleDto {
    private MultipartFile profilResmi;

    public ProfilResmiGuncelleDto() {}

    public ProfilResmiGuncelleDto(MultipartFile profilResmi) {
        this.profilResmi = profilResmi;
    }

    public MultipartFile getProfilResmi() {
        return profilResmi;
    }

    public void setProfilResmi(MultipartFile profilResmi) {
        this.profilResmi = profilResmi;
    }
}
