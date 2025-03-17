package com.bitirmeproje.service.follows;

import com.bitirmeproje.dto.follows.PopulerKullaniciDto;

import java.util.List;

public interface IFollowsService {

    List<PopulerKullaniciDto> populerKullanicilariGetir();
}
