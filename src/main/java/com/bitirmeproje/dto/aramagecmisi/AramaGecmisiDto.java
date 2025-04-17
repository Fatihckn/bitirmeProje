package com.bitirmeproje.dto.aramagecmisi;

import java.time.LocalDateTime;

public record AramaGecmisiDto(int aramaGecmisiId, String aramaIcerigi, LocalDateTime aramaZamani) {
}
