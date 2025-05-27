package com.bitirmeproje.dto.gonderiler;

public class YapayZekaResimEkleDto extends GonderiEkleDto{
    private String resimUrl;

    public YapayZekaResimEkleDto(String resimUrl){
        super();
        this.resimUrl = resimUrl;
    }

    public String getResimUrl() {
        return resimUrl;
    }

    public void setResimUrl(String resimUrl) {
        this.resimUrl = resimUrl;
    }
}
