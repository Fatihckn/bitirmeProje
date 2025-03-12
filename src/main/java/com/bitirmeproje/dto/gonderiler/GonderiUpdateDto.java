package com.bitirmeproje.dto.gonderiler;


public class GonderiUpdateDto {

    private String gonderiIcerigi;

    public GonderiUpdateDto() {}

    public GonderiUpdateDto(String gonderiIcerigi) {
        this.gonderiIcerigi = gonderiIcerigi;
    }

    public String getGonderiIcerigi() {
        return gonderiIcerigi;
    }

    public void setGonderiIcerigi(String gonderiIcerigi) {
        this.gonderiIcerigi = gonderiIcerigi;
    }
}
