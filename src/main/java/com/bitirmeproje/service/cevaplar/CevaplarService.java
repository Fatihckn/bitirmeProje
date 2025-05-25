package com.bitirmeproje.service.cevaplar;

import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Anketler;
import com.bitirmeproje.model.Cevaplar;
import com.bitirmeproje.model.Secenekler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.AnketlerRepository;
import com.bitirmeproje.repository.CevaplarRepository;
import com.bitirmeproje.repository.SeceneklerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CevaplarService implements ICevaplarService {
    CevaplarRepository cevaplarRepository;
    GetUserByToken getUserByToken;
    SeceneklerRepository seceneklerRepository;
    AnketlerRepository anketlerRepository;

    public CevaplarService(CevaplarRepository cevaplarRepository, GetUserByToken getUserByToken,
                           SeceneklerRepository seceneklerRepository, AnketlerRepository anketlerRepository) {
        this.cevaplarRepository = cevaplarRepository;
        this.getUserByToken = getUserByToken;
        this.seceneklerRepository = seceneklerRepository;
        this.anketlerRepository = anketlerRepository;
    }

    public void cevapKaydet(int anketId, int secenekId){
        User user = getUserByToken.getUser();

        Secenekler secenek = seceneklerRepository.findSeceneklerBySecenekId(secenekId);

        Anketler anket = anketlerRepository.findAnketByAnketId(anketId);

        if(secenek == null || anket == null || secenek.getAnketId().getAnketId() != anket.getAnketId()){
            throw new CustomException(HttpStatus.NOT_FOUND,"Gecersiz Anket veya Secenek!");
        }

        Cevaplar cevap = mapperCevaplar(anket,user,secenek);

        cevaplarRepository.save(cevap);
    }

    private Cevaplar mapperCevaplar(Anketler anket, User user, Secenekler secenek){
        Cevaplar cevaplar = new Cevaplar();
        cevaplar.setAnketId(anket);
        cevaplar.setKullaniciId(user);
        cevaplar.setSecenekId(secenek);
        cevaplar.setCevapTarih(LocalDateTime.now());
        return  cevaplar;
    }
}
