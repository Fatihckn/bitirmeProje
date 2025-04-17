package com.bitirmeproje.service.home;

import com.bitirmeproje.dto.home.HomeDto;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.HomeRepository;
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

        List<HomeDto> gonderiler = homeRepository.getGonderiler(user.getKullaniciId());

        gonderiler.forEach(homeDto -> homeDto.setKullaniciResim(homeDto.getKullaniciResim()));

        return gonderiler;
    }
}

