package com.bitirmeproje.service.mesaj;

import com.bitirmeproje.dto.mesaj.KullanicininSonGelenMesajlari;
import com.bitirmeproje.dto.mesaj.MesajCreateDto;
import com.bitirmeproje.dto.mesaj.MesajDto;
import com.bitirmeproje.dto.mesaj.MesajSohbetGecmisiGetirDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Mesaj;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.MesajRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MesajService implements IMesajService {

    private final MesajRepository mesajRepository;
    private final FindUser<Integer> findUser;
    private final GetUserByToken getUserByToken;
    private final SimpMessagingTemplate messagingTemplate;

    public MesajService(MesajRepository mesajRepository,
                        @Qualifier("findUserById") FindUser<Integer> findUser,
                        GetUserByToken getUserByToken, SimpMessagingTemplate messagingTemplate) {
        this.mesajRepository = mesajRepository;
        this.findUser = findUser;
        this.getUserByToken = getUserByToken;
        this.messagingTemplate = messagingTemplate;
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
        mesaj.setMesajGonderilmeZamani(LocalDateTime.now());

        mesajRepository.save(mesaj);

        MesajDto mesajDto = new MesajDto(mesaj);

        // ✨ WebSocket ile sadece alıcıya mesaj gönder
        messagingTemplate.convertAndSendToUser(
                alici.getKullaniciTakmaAd(),   // User destination key (ID'yi string yapıyoruz)
                "/queue/mesajlar",                   // Kendi kuyruğu
                mesajDto
        );

        return mesajDto;
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
    public List<MesajSohbetGecmisiGetirDto> sohbetGecmisiGetir(int kullaniciId) {
        User kullanici1 = getUserByToken.getUser();
        User kullanici2 = findUser.findUser(kullaniciId);

        return mesajRepository.findSohbetGecmisi(kullanici1, kullanici2)
                .stream()
                .map(mesaj -> new MesajSohbetGecmisiGetirDto(
                        mesaj,
                        kullanici2.getKullaniciTakmaAd(),
                        kullanici2.getKullaniciProfilResmi(),
                        kullanici1.getKullaniciTakmaAd()
                ))
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

    public List<KullanicininSonGelenMesajlari> getKullanicininSonGelenMesajlari() {
        User user = getUserByToken.getUser();

        return mesajRepository.findSonKonusmalar(user.getKullaniciId());
    }

    private Mesaj getMesaj(int mesajId){
        Mesaj mesaj = mesajRepository.findByMesajId(mesajId);

        if (mesaj == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Mesaj Bulunamadi");
        }
        return mesaj;
    }
}
