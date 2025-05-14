package com.bitirmeproje.dto.anketler;

import com.bitirmeproje.dto.secenekler.SeceneklerDtoWithCevapSayisi;

import java.time.LocalDateTime;
import java.util.List;

public class GirisYapanKullaniciAnketDto {
    private int anketId;

    private String anketSorusu;

    private LocalDateTime anketOlusturulmaTarihi;

    private List<SeceneklerDtoWithCevapSayisi> secenekler;

    public GirisYapanKullaniciAnketDto() {}

    public GirisYapanKullaniciAnketDto(int anketId, String anketSorusu, LocalDateTime anketOlusturulmaTarihi, List<SeceneklerDtoWithCevapSayisi> secenekler) {
        this.anketId = anketId;
        this.anketSorusu = anketSorusu;
        this.anketOlusturulmaTarihi = anketOlusturulmaTarihi;
        this.secenekler = secenekler;
    }

    public int getAnketId() {
        return anketId;
    }

    public void setAnketId(int anketId) {
        this.anketId = anketId;
    }

    public String getAnketSorusu() {
        return anketSorusu;
    }

    public void setAnketSorusu(String anketSorusu) {
        this.anketSorusu = anketSorusu;
    }

    public LocalDateTime getAnketOlusturulmaTarihi() {
        return anketOlusturulmaTarihi;
    }

    public void setAnketOlusturulmaTarihi(LocalDateTime anketOlusturulmaTarihi) {
        this.anketOlusturulmaTarihi = anketOlusturulmaTarihi;
    }

    public List<SeceneklerDtoWithCevapSayisi> getSecenekler() {
        return secenekler;
    }

    public void setSecenekler(List<SeceneklerDtoWithCevapSayisi> secenekler) {
        this.secenekler = secenekler;
    }
}
