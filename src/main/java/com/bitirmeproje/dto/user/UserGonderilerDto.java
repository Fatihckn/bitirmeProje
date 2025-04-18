package com.bitirmeproje.dto.user;

import com.bitirmeproje.dto.gonderiler.GonderiDto;

import java.util.List;

public class UserGonderilerDto extends BaseUserDto{
    private List<GonderiDto> gonderiler;

    public UserGonderilerDto(List<GonderiDto> gonderiler) {
        super();
        this.gonderiler = gonderiler;
    }

    public List<GonderiDto> getGonderiler() {
        return gonderiler;
    }

    public void setGonderiler(List<GonderiDto> gonderiler) {
        this.gonderiler = gonderiler;
    }
}
