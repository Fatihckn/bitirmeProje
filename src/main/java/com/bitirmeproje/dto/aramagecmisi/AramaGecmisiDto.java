package com.bitirmeproje.dto.aramagecmisi;

import java.time.LocalDate;

public class AramaGecmisiDto {
    private int aramaGecmisiId;
    private String aramaIcerigi;
    private LocalDate aramaZamani;

    public AramaGecmisiDto(int aramaGecmisiId, String aramaIcerigi, LocalDate aramaZamani) {
        this.aramaGecmisiId = aramaGecmisiId;
        this.aramaIcerigi = aramaIcerigi;
        this.aramaZamani = aramaZamani;
    }

    public int getAramaGecmisiId() {
        return aramaGecmisiId;
    }

    public String getAramaIcerigi() {
        return aramaIcerigi;
    }

    public LocalDate getAramaZamani() {
        return aramaZamani;
    }
}
