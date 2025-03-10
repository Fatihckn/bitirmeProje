package com.bitirmeproje.dto;

import java.time.LocalDate;

public class AramaGecmisiDto {
    private int aramaGecmisiId;
    private String aramaIcerigi;
    private LocalDate aramaZamani;
    private int kullaniciId;

    public AramaGecmisiDto(int aramaGecmisiId, String aramaIcerigi, LocalDate aramaZamani, int kullaniciId) {
        this.aramaGecmisiId = aramaGecmisiId;
        this.aramaIcerigi = aramaIcerigi;
        this.aramaZamani = aramaZamani;
        this.kullaniciId = kullaniciId;
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

    public int getKullaniciId() {
        return kullaniciId;
    }
}
