package com.bitirmeproje.service;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.gonderiler.GonderiResponseDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.GonderilerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GonderilerService implements IGonderilerService {

    private final GonderilerRepository gonderilerRepository;
    private final FindUser<Integer> findUser;

    public GonderilerService(GonderilerRepository gonderilerRepository,@Qualifier("findUserById") FindUser<Integer> findUser) {
        this.gonderilerRepository = gonderilerRepository;
        this.findUser = findUser;
    }

    // Belirli bir kullanıcının tüm gönderilerini getir
    public List<Gonderiler> kullaniciGonderileriniGetir(int kullaniciId) {
        return gonderilerRepository.findByKullaniciId_KullaniciId(kullaniciId);
    }


    public void begeniEkle(int gonderiId) {
        Gonderiler gonderi = findGonderi(gonderiId);

        gonderi.setGonderiBegeniSayisi(gonderi.getGonderiBegeniSayisi() + 1);
        gonderilerRepository.save(gonderi);
    }

    public void begeniKaldir(int gonderiId) {
        Gonderiler gonderi = findGonderi(gonderiId);

        if (gonderi.getGonderiBegeniSayisi() > 0) {
            gonderi.setGonderiBegeniSayisi(gonderi.getGonderiBegeniSayisi() - 1);
        }
        gonderilerRepository.save(gonderi);
    }

    // Yeni gönderi ekle
    public void yeniGonderiEkle(int kullaniciId, GonderiDto gonderiDto) {

        User user = findUser.findUser(kullaniciId);

        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiIcerigi(gonderiDto.getGonderiIcerigi());
        gonderi.setGonderiBegeniSayisi(0);
        gonderi.setGonderiTarihi(LocalDate.now());
        gonderi.setKullaniciId(user);
        gonderilerRepository.save(gonderi);
    }

    // En popüler gönderileri getir
    public List<GonderiResponseDto> populerGonderileriGetir() {
        return gonderilerRepository.findPopularPosts();
    }

    // Gönderiyi sil
    public void gonderiSil(int gonderiId) {
        Gonderiler gonderi = findGonderi(gonderiId);

        gonderilerRepository.deleteById(gonderi.getGonderiId());
    }

    // Gönderi içeriğini güncelle
    public void gonderiGuncelle(int gonderiId, String yeniIcerik) {
        Gonderiler gonderi = findGonderi(gonderiId);

        if (yeniIcerik.isEmpty()){
            throw new CustomException(HttpStatus.NOT_FOUND,"İçerik boş olamaz");
        }

        gonderi.setGonderiIcerigi(yeniIcerik);
        gonderilerRepository.save(gonderi);
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

    private Gonderiler findGonderi(int gonderiId) {
        return gonderilerRepository.findById(gonderiId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı!"));
    }
}
