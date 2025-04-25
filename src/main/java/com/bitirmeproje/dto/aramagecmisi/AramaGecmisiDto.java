package com.bitirmeproje.dto.aramagecmisi;

import java.time.LocalDateTime;

public record AramaGecmisiDto(int aramaGecmisiId, int arananKullaniciId, LocalDateTime aramaZamani, String kullaniciProfilResmi, String kullaniciTakmaAd) {
}
