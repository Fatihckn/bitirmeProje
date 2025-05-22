package com.bitirmeproje.service.anketler;

import com.bitirmeproje.dto.anketler.AnketlerSaveDto;
import com.bitirmeproje.dto.anketler.GirisYapanKullaniciAnketDto;
import com.bitirmeproje.dto.secenekler.SeceneklerDtoWithCevapSayisi;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Anketler;
import com.bitirmeproje.model.Secenekler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.AnketlerRepository;
import com.bitirmeproje.repository.SeceneklerRepository;
import com.bitirmeproje.service.pythonservice.PythonApiService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AnketlerService implements IAnketlerService {
    private final AnketlerRepository anketlerRepository;
    private final IEntityDtoConverter<Anketler, AnketlerSaveDto> entityDtoConverter;
    private final GetUserByToken getUserByToken;
    private final SeceneklerRepository seceneklerRepository;
    private final PythonApiService pythonApiService;

    public AnketlerService(AnketlerRepository anketlerRepository,@Qualifier("anketlerConverter") IEntityDtoConverter<Anketler,
            AnketlerSaveDto> entityDtoConverter, GetUserByToken getUserByToken, SeceneklerRepository seceneklerRepository,
                           PythonApiService pythonApiService) {
        this.anketlerRepository = anketlerRepository;
        this.entityDtoConverter = entityDtoConverter;
        this.getUserByToken = getUserByToken;
        this.seceneklerRepository = seceneklerRepository;
        this.pythonApiService = pythonApiService;
    }

    public Anketler anketlerSave(AnketlerSaveDto anketlerDto) {
        Anketler anketler = entityDtoConverter.convertToEntity(anketlerDto);

        saveSoruSecenekleri(anketlerDto.getSoruSecenekleri(), anketler);

        return anketlerRepository.save(anketler);
    }

    public List<GirisYapanKullaniciAnketDto> getKullaniciAnketler(){
        User user = getUserByToken.getUser();

        List<Anketler> kullaniciAnketleri = anketlerRepository.findAnketlerByKullaniciOrderByOlusturulmaTarihiDesc(user);

        return kullaniciAnketleri.stream()
                .map(anketler -> {
                    List<SeceneklerDtoWithCevapSayisi> seceneklerDtoWithCevapSayisiList = seceneklerRepository.getSeceneklerByAnketId(anketler.getAnketId());

                    return girisYapanKullaniciAnketDtoMapper(anketler, seceneklerDtoWithCevapSayisiList);
                })
                .toList();
    }

    @Transactional
    public void deleteAnket(int anketId) {
        User user = getUserByToken.getUser();

        Anketler kullanicininAnketi = anketlerRepository.findAnketByAnketId(anketId);

        if(kullanicininAnketi.getKullanici().getKullaniciId() != user.getKullaniciId()){
            throw new CustomException(HttpStatus.NOT_FOUND,"Anket Bulunamadi");
        }

        anketlerRepository.delete(kullanicininAnketi);
    }

    public List<GirisYapanKullaniciAnketDto> kullaniciAnketOneri() {
        User user = getUserByToken.getUser();

        List<Integer> anketler = pythonApiService.getAnketOnerileri(user.getKullaniciId());

        return anketler.stream()
                .map(anketlerRepository::findAnketByAnketId)
                .filter(Objects::nonNull)
                .map(anket -> {
                    List<SeceneklerDtoWithCevapSayisi> secenekler =
                            seceneklerRepository.getSeceneklerByAnketId(anket.getAnketId());
                    return girisYapanKullaniciAnketDtoMapper(anket, secenekler);
                })
                .collect(Collectors.toList());
    }

    private void saveSoruSecenekleri(List<String> soruSecenekleri, Anketler anketler) {
        // Stringleri Secenekler nesnesine çeviriyoruz
        List<Secenekler> seceneklerList = soruSecenekleri
                .stream()
                .map(secenekStr -> {
                    Secenekler secenek = new Secenekler();
                    secenek.setSecenekMetni(secenekStr);
                    secenek.setAnketId(anketler);
                    return secenek;
                })
                .toList();

        anketler.setSecenekler(seceneklerList);
    }

    private GirisYapanKullaniciAnketDto girisYapanKullaniciAnketDtoMapper(Anketler anketler, List<SeceneklerDtoWithCevapSayisi> seceneklerDtoWithCevapSayisiList){
        GirisYapanKullaniciAnketDto girisYapanKullaniciAnketDto = new GirisYapanKullaniciAnketDto();
        girisYapanKullaniciAnketDto.setAnketId(anketler.getAnketId());
        girisYapanKullaniciAnketDto.setAnketSorusu(anketler.getAnketSorusu());
        girisYapanKullaniciAnketDto.setAnketOlusturulmaTarihi(anketler.getOlusturulmaTarihi());
        girisYapanKullaniciAnketDto.setSecenekler(seceneklerDtoWithCevapSayisiList);
        return girisYapanKullaniciAnketDto;
    }
}
