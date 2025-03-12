package com.bitirmeproje.dto.gonderiler;

public class GonderiDto {
    private String gonderiIcerigi;

    public GonderiDto() {}

    public GonderiDto(String gonderiIcerigi) {
        this.gonderiIcerigi = gonderiIcerigi;
    }

    public String getGonderiIcerigi() {
        return gonderiIcerigi;
    }

    public void setGonderiIcerigi(String gonderiIcerigi) {
        this.gonderiIcerigi = gonderiIcerigi;
    }
}
