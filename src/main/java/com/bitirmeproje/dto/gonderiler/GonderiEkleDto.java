package com.bitirmeproje.dto.gonderiler;

import org.springframework.web.multipart.MultipartFile;

public class GonderiEkleDto {
    protected String gonderiIcerigi;
    private MultipartFile gonderiMedyaUrl;
    protected String gonderiMedyaTuru;

    public GonderiEkleDto() {}

    public GonderiEkleDto(String gonderiIcerigi, MultipartFile gonderiMedyaUrl,
                          String gonderiMedyaTuru) {
        this.gonderiIcerigi = gonderiIcerigi;
        this.gonderiMedyaUrl = gonderiMedyaUrl;
        this.gonderiMedyaTuru = gonderiMedyaTuru;
    }

    public String getGonderiIcerigi() {
        return gonderiIcerigi;
    }

    public void setGonderiIcerigi(String gonderiIcerigi) {
        this.gonderiIcerigi = gonderiIcerigi;
    }

    public MultipartFile getGonderiMedyaUrl() {
        return gonderiMedyaUrl;
    }

    public void setGonderiMedyaUrl(MultipartFile gonderiMedyaUrl) {
        this.gonderiMedyaUrl = gonderiMedyaUrl;
    }

    public String getGonderiMedyaTuru() {
        return gonderiMedyaTuru;
    }

    public void setGonderiMedyaTuru(String gonderiMedyaTuru) {
        this.gonderiMedyaTuru = gonderiMedyaTuru;
    }
}
