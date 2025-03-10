package com.bitirmeproje.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BegenilenGonderilerDto {
    @JsonProperty("gonderiId") // JSON dönüşümü için gerekli
    private int gonderiId;

    // 📌 Boş Constructor (Spring Boot JSON dönüşümü için şart!)
    public BegenilenGonderilerDto() {
    }

    // 📌 Parametreli Constructor
    public BegenilenGonderilerDto(int gonderiId) {
        this.gonderiId = gonderiId;
    }

    public int getGonderiId() {
        return gonderiId;
    }

    public void setGonderiId(int gonderiId) {
        this.gonderiId = gonderiId;
    }
}
