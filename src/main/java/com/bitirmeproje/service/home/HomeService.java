package com.bitirmeproje.service.home;

import com.bitirmeproje.dto.home.HomeDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.HomeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService implements IHomeService {

    private final HomeRepository homeRepository;
    private final GetUserByToken getUserByToken;

    public HomeService(HomeRepository homeRepository,
                       GetUserByToken getUserByToken) {
        this.homeRepository = homeRepository;
        this.getUserByToken = getUserByToken;
    }

    // Kullanıcının takip ettiği kişilerin gönderilerini getir.
    public List<HomeDto> getHome() {
        User user = getUserByToken.getUser();
        System.out.println("user id= "+user.getKullaniciId());

        List<HomeDto> gonderiler = homeRepository.getGonderiler(user.getKullaniciId());
        System.out.println(gonderiler.isEmpty());


        if(gonderiler.isEmpty()){
            throw new CustomException(HttpStatus.NOT_FOUND,"Gonderi Bulunamadi");
        }

        return gonderiler;
    }
}

