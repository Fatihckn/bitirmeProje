package com.bitirmeproje.service;

import com.bitirmeproje.dto.mesaj.MesajCreateDto;
import com.bitirmeproje.dto.mesaj.MesajDto;
import com.bitirmeproje.dto.mesaj.MesajUpdateDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.Mesaj;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.MesajRepository;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MesajService implements IMesajService {

    private final MesajRepository mesajRepository;
    private final UserRepository userRepository;

    public MesajService(MesajRepository mesajRepository, UserRepository userRepository) {
        this.mesajRepository = mesajRepository;
        this.userRepository = userRepository;
    }

    // Yeni mesaj gönderme
    @Override
    public MesajDto mesajGonder(MesajCreateDto mesajCreateDto) {
        User gonderen = userRepository.findById(mesajCreateDto.getMesajGonderenKullaniciId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Gönderen kullanıcı bulunamadı!"));

        User alici = userRepository.findById(mesajCreateDto.getMesajGonderilenKullaniciId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Alıcı kullanıcı bulunamadı!"));

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
    public List<MesajDto> gelenMesajlariListele(int kullaniciId) {
        User user = userRepository.findById(kullaniciId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!"));

        return mesajRepository.findByMesajGonderilenKullaniciId(user)
                .stream()
                .map(MesajDto::new)
                .collect(Collectors.toList());
    }

    // Kullanıcının gönderdiği mesajları listele
    @Override
    public List<MesajDto> gonderilenMesajlariListele(int kullaniciId) {
        User user = userRepository.findById(kullaniciId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!"));

        return mesajRepository.findByMesajGonderenKullaniciId(user)
                .stream()
                .map(MesajDto::new)
                .collect(Collectors.toList());
    }

    // ID'ye göre mesaj getir
    @Override
    public Optional<MesajDto> mesajGetir(int mesajId) {
        return mesajRepository.findById(mesajId).map(MesajDto::new);
    }

    // İki kullanıcı arasındaki sohbet geçmişini getir
    @Override
    public List<MesajDto> sohbetGecmisiGetir(int kullanici1Id, int kullanici2Id) {
        User kullanici1 = userRepository.findById(kullanici1Id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı 1 bulunamadı!"));

        User kullanici2 = userRepository.findById(kullanici2Id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı 2 bulunamadı!"));

        return mesajRepository.findSohbetGecmisi(kullanici1, kullanici2)
                .stream()
                .map(MesajDto::new)
                .collect(Collectors.toList());
    }

    // Mesaj içeriğini güncelle
    @Override
    public void mesajGuncelle(int mesajId, MesajUpdateDto mesajUpdateDto) {
        Mesaj mesaj = mesajRepository.findById(mesajId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Mesaj bulunamadı!"));

        mesaj.setMesajIcerigi(mesajUpdateDto.getMesajIcerigi());
        mesajRepository.save(mesaj);
    }


    // Belirli bir mesajı sil
    @Override
    public void mesajSil(int mesajId) {
        if (!mesajRepository.existsById(mesajId)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Mesaj bulunamadı!");
        }
        mesajRepository.deleteById(mesajId);
    }

    // Belirli bir kullanıcı ile olan tüm mesajları sil
    @Override
    public void tumMesajlariSil(int kullanici1Id, int kullanici2Id) {
        User kullanici1 = userRepository.findById(kullanici1Id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı 1 bulunamadı!"));

        User kullanici2 = userRepository.findById(kullanici2Id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı 2 bulunamadı!"));

        mesajRepository.deleteByMesajGonderenKullaniciIdOrMesajGonderilenKullaniciId(kullanici1, kullanici2);
    }
}
