package com.bitirmeproje.dto.begenilengonderiler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class BegenilenGonderilerDto {
    @JsonProperty("gonderiId") // JSON dönüşümü için gerekli
    private int gonderiId;

    private int begenilenGonderiId;

    private LocalDate begenmeZamani;

    // 📌 Boş Constructor (Spring Boot JSON dönüşümü için şart!)
    public BegenilenGonderilerDto() {
    }

    // 📌 Parametreli Constructor
    public BegenilenGonderilerDto(int gonderiId, int begenilenGonderiId, LocalDate begenmeZamani) {
        this.gonderiId = gonderiId;
        this.begenilenGonderiId = begenilenGonderiId;
        this.begenmeZamani = begenmeZamani;
    }

    public int getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(int gonderiId) {
        this.gonderiId = gonderiId;
    }

    public int getBegenilenGonderiId() {
        return begenilenGonderiId;
    }

    public void setBegenilenGonderiId(int begenilenGonderiId) {
        this.begenilenGonderiId = begenilenGonderiId;
    }

    public LocalDate getBegenmeZamani() {
        return begenmeZamani;
    }

    public void setBegenmeZamani(LocalDate begenmeZamani) {
        this.begenmeZamani = begenmeZamani;
    }
}
