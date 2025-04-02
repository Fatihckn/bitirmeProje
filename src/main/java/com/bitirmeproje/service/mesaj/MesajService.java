package com.bitirmeproje.service.mesaj;

import com.bitirmeproje.dto.mesaj.MesajCreateDto;
import com.bitirmeproje.dto.mesaj.MesajDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Mesaj;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.MesajRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MesajService implements IMesajService {

    private final MesajRepository mesajRepository;
    private final FindUser<Integer> findUser;
    private final GetUserByToken getUserByToken;

    public MesajService(MesajRepository mesajRepository,
                        @Qualifier("findUserById") FindUser<Integer> findUser,
                        GetUserByToken getUserByToken) {
        this.mesajRepository = mesajRepository;
        this.findUser = findUser;
        this.getUserByToken = getUserByToken;
    }

    // Yeni mesaj gönderme
    @Override
    public MesajDto mesajGonder(MesajCreateDto mesajCreateDto) {
        User gonderen = getUserByToken.getUser();

        User alici = findUser.findUser(mesajCreateDto.getMesajGonderilenKullaniciId());

        Mesaj mesaj = new Mesaj();
        mesaj.setMesajGonderenKullaniciId(gonderen);
        mesaj.setMesajGonderilenKullaniciId(alici);
        mesaj.setMesajIcerigi(mesajCreateDto.getMesajIcerigi());
        mesaj.setMesajGonderilmeZamani(LocalDate.now());

        mesajRepository.save(mesaj);

        return new MesajDto(mesaj);
    }

    // Kullanıcının gelen mesajlarını listele
    @Override
    public List<MesajDto> gelenMesajlariListele() {
        User user = getUserByToken.getUser();

        return mesajRepository.findByMesajGonderilenKullaniciId(user)
                .stream()
                .map(MesajDto::new)
                .collect(Collectors.toList());
    }

    // Kullanıcının gönderdiği mesajları listele
    @Override
    public List<MesajDto> gonderilenMesajlariListele() {
        User user = getUserByToken.getUser();

        return mesajRepository.findByMesajGonderenKullaniciId(user)
                .stream()
                .map(MesajDto::new)
                .collect(Collectors.toList());
    }

    // ID'ye göre mesaj getir
    @Override
    public Optional<MesajDto> mesajGetir(int mesajId) {
        Mesaj mesaj = getMesaj(mesajId);

        User user = getUserByToken.getUser();

        // Birbiri ile takipleşen kullanıcılar mesajlarını getirebilsin.
        if (!mesaj.getMesajGonderenKullaniciId().equals(user) && !mesaj.getMesajGonderilenKullaniciId().equals(user)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Mesaj Bulunamadi");
        }

        return Optional.of(mesaj)
                .map(MesajDto::new);
    }

    // İki kullanıcı arasındaki sohbet geçmişini getir
    @Override
    public List<MesajDto> sohbetGecmisiGetir(int kullaniciId) {
        User kullanici1 = getUserByToken.getUser(); // Giriş yapan kullanıcı
        User kullanici2 = findUser.findUser(kullaniciId); // Mesajlaştığı kişi

        return mesajRepository.findSohbetGecmisi(kullanici1, kullanici2)
                .stream()
                .map(MesajDto::new)
                .collect(Collectors.toList());
    }

    // Mesaj içeriğini güncelle
    @Override
    public void mesajGuncelle(int mesajId, MesajDto mesajDto) {
        Mesaj mesaj = getMesaj(mesajId);

        // Kendi attığı mesaj değilse bulunamadı fırlatılsın.
        if(!mesaj.getMesajGonderenKullaniciId().equals(getUserByToken.getUser())) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Mesaj Bulunamadi");
        }

        mesaj.setMesajIcerigi(mesajDto.getMesajIcerigi());
        mesajRepository.save(mesaj);
    }


    // Belirli bir mesajı sil
    @Override
    public void mesajSil(int mesajId) {
        Mesaj mesaj = getMesaj(mesajId);

        // Kendi attığı mesaj değilse bulunamadı fırlatılsın.
        if(!mesaj.getMesajGonderenKullaniciId().equals(getUserByToken.getUser())) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Mesaj Bulunamadi");
        }

        mesajRepository.deleteById(mesajId);
    }

    // Belirli bir kullanıcı ile olan tüm mesajları sil
    @Override
    @Transactional
    public void tumMesajlariSil(int kullaniciId) {
        User kullanici1 = getUserByToken.getUser();

        User kullanici2 = findUser.findUser(kullaniciId);

        mesajRepository.deleteByMesajGonderenKullaniciIdOrMesajGonderilenKullaniciId(kullanici1, kullanici2);
    }

    private Mesaj getMesaj(int mesajId){
        Mesaj mesaj = mesajRepository.findByMesajId(mesajId);

        if (mesaj == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Mesaj Bulunamadi");
        }
        return mesaj;
    }
}
