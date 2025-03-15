package com.bitirmeproje.service;

import com.bitirmeproje.dto.home.HomeDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.HomeRepository;
import com.bitirmeproje.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    private final HomeRepository homeRepository;
    private final JwtUtil jwtUtil;
    private final FindUser<Integer> findUser;

    public HomeService(HomeRepository homeRepository, JwtUtil jwtUtil,
                       @Qualifier("findUserById") FindUser<Integer> findUser) {
        this.homeRepository = homeRepository;
        this.jwtUtil = jwtUtil;
        this.findUser = findUser;
    }

    // Kullanıcının takip ettiği kişilerin gönderilerini getir.
    public List<HomeDto> getHome() {
        User user = findUser.findUser(jwtUtil.extractUserId());

        List<Gonderiler> gonderiler = homeRepository.getGonderiler(user.getKullaniciId());

        if(gonderiler.isEmpty()){
            throw new CustomException(HttpStatus.NOT_FOUND,"Gonderi Bulunamadi");
        }

        return gonderiler.stream()
                .map(this::convertToHomeDto) // Her bir gönderiyi HomeDto'ya çevir
                .collect(Collectors.toList());
    }

    private HomeDto convertToHomeDto(Gonderiler gonderi) {
        return new HomeDto(
                gonderi.getGonderiId(),
                gonderi.getKullaniciId().getKullaniciId(),
                gonderi.getGonderiIcerigi(),
                gonderi.getGonderiBegeniSayisi(),
                gonderi.getGonderiTarihi()
        );
    }
}
