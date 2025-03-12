package com.bitirmeproje.service;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderiResponseDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GonderilerService {

    private final GonderilerRepository gonderilerRepository;
    private final UserRepository userRepository;

    public GonderilerService(GonderilerRepository gonderilerRepository, UserRepository userRepository) {
        this.gonderilerRepository = gonderilerRepository;
        this.userRepository = userRepository;
    }

    // Belirli bir kullanıcının tüm gönderilerini getir
    public List<Gonderiler> kullaniciGonderileriniGetir(int kullaniciId) {
        return gonderilerRepository.findByKullaniciId_KullaniciId(kullaniciId);
    }


    public void begeniEkle(int gonderiId) {
        Gonderiler gonderi = gonderilerRepository.findById(gonderiId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı!"));

        gonderi.setGonderiBegeniSayisi(gonderi.getGonderiBegeniSayisi() + 1);
        gonderilerRepository.save(gonderi);
    }

    public void begeniKaldir(int gonderiId) {
        Gonderiler gonderi = gonderilerRepository.findById(gonderiId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı!"));

        if (gonderi.getGonderiBegeniSayisi() > 0) {
            gonderi.setGonderiBegeniSayisi(gonderi.getGonderiBegeniSayisi() - 1);
        }
        gonderilerRepository.save(gonderi);
    }

    // Yeni gönderi ekle
    public Gonderiler yeniGonderiEkle(int kullaniciId, GonderiDto gonderiDto) {
        Optional<User> userOptional = userRepository.findById(kullaniciId);

        if (userOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!");
        }

        User user = userOptional.get();

        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiIcerigi(gonderiDto.getGonderiIcerigi());
        gonderi.setGonderiBegeniSayisi(0);
        gonderi.setGonderiTarihi(LocalDate.now());
        gonderi.setKullaniciId(user);

        return gonderilerRepository.save(gonderi);
    }



    // En popüler gönderileri getir
    public List<GonderiResponseDto> populerGonderileriGetir() {
        return gonderilerRepository.findPopularPosts();
    }

    // Gönderiyi sil
    public void gonderiSil(int gonderiId) {
        Optional<Gonderiler> gonderiOptional = gonderilerRepository.findById(gonderiId);

        if (gonderiOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı!");
        }

        gonderilerRepository.deleteById(gonderiId);
    }

    // Gönderi içeriğini güncelle
    public Gonderiler gonderiGuncelle(int gonderiId, String yeniIcerik) {
        Optional<Gonderiler> gonderiOptional = gonderilerRepository.findById(gonderiId);

        if (gonderiOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı!");
        }

        Gonderiler gonderi = gonderiOptional.get();
        gonderi.setGonderiIcerigi(yeniIcerik);
        return gonderilerRepository.save(gonderi);
    }


/*    public void yorumEkle(int gonderiId, String yorumIcerigi) {
        Gonderiler gonderi = gonderilerRepository.findById(gonderiId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı!"));

        YeniYorum yeniYorum = new YeniYorum();
        yeniYorum.setGonderiId(gonderi);
        yeniYorum.setYorumIcerigi(yorumIcerigi);
        yeniYorum.setYorumTarihi(LocalDate.now());

        yeniYorumRepository.save(yeniYorum);
    }
*/
}
