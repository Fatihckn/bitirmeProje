package com.bitirmeproje.dto.begenilengonderiler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class BegenilenGonderilerDto {
    @JsonProperty("gonderiId") // JSON dönüşümü için gerekli
    private int gonderiId;

    private int begenilenGonderiId;

    private LocalDateTime begenmeZamani;

    public BegenilenGonderilerDto() {}

    public BegenilenGonderilerDto(int gonderiId, int begenilenGonderiId, LocalDateTime begenmeZamani) {
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

    public LocalDateTime getBegenmeZamani() {
        return begenmeZamani;
    }

    public void setBegenmeZamani(LocalDateTime begenmeZamani) {
        this.begenmeZamani = begenmeZamani;
    }
}
