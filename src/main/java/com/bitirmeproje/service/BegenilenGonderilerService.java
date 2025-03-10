package com.bitirmeproje.service;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.BegenilenGonderiler;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.BegenilenGonderilerRepository;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bitirmeproje.dto.BegenilenGonderilerDto;

import java.util.List;

@Service
public class BegenilenGonderilerService {

    private final BegenilenGonderilerRepository begenilenGonderilerRepository;
    private final UserRepository userRepository;

    public BegenilenGonderilerService(BegenilenGonderilerRepository begenilenGonderilerRepository, UserRepository userRepository)//Repoyla bağlılığı sağlandı heralde
    {
        this.begenilenGonderilerRepository=begenilenGonderilerRepository;
        this.userRepository=userRepository;
    }

    // Gönderi Beğenme
    public void begeniEkle(int gonderiId, String kullaniciEmail) {
        // Kullanıcıyı email ile bul
        User kullanici = userRepository.findByEPosta(kullaniciEmail)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!"));

        // Gonderiler nesnesini oluşturan metod çağırılıyor
        Gonderiler gonderi = getGonderiById(gonderiId);

        // Kullanıcının bu gönderiyi daha önce beğenip beğenmediğini kontrol et
        if (begenilenGonderilerRepository.existsByGonderiIdAndKullaniciId(gonderi, kullanici)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu gönderiyi zaten beğendiniz!");
        }

        // Yeni beğeni kaydı oluştur
        BegenilenGonderiler begeni = createBegeni(gonderi, kullanici);

        // Kaydet
        begenilenGonderilerRepository.save(begeni);
    }

    // Gönderi ID ile gönderi nesnesi oluşturuluyor.
    private Gonderiler getGonderiById(int gonderiId) {
        Gonderiler gonderi = new Gonderiler();
        gonderi.setGonderiId(gonderiId);
        return gonderi;
    }

    // Beğenilen gönderi nesnesi oluşturulup field'larına set ediliyor.
    private BegenilenGonderiler createBegeni(Gonderiler gonderi, User kullanici) {
        BegenilenGonderiler begeni = new BegenilenGonderiler();
        begeni.setGonderiId(gonderi);
        begeni.setKullaniciId(kullanici);
        return begeni;
    }

    // Gönderiden beğeniyi kaldır (Repository'de delete işlemi Entity manager üzerinden çalıştığı için transaction kontrölünü eklememiz lazım)
    @Transactional
    public void begeniKaldir(int gonderiId,String kullaniciEmail) {

        // Kullanıcıyı email ile bul
        User kullanici = userRepository.findByEPosta(kullaniciEmail)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!"));

        Gonderiler gonderi = getGonderiById(gonderiId);

        // Kullanıcının bu gönderiyi daha önce beğenip beğenmediğini kontrol et
        if (!begenilenGonderilerRepository.existsByGonderiIdAndKullaniciId(gonderi, kullanici)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu gönderiyi zaten beğenmiyorsunuz!");
        }

        begenilenGonderilerRepository.deleteByGonderiIdAndKullaniciId(gonderi, kullanici);
    }

    // Gönderinin beğeni sayısı
    public int gonderiBegeniSayisi(int gonderiId) {
        Gonderiler gonderi = getGonderiById(gonderiId);
        gonderi.setGonderiId(gonderiId);
        return begenilenGonderilerRepository.countByGonderiId(gonderi);
    }

    //belirli bir kullanıcının beğendiği gönderiler
    public List<BegenilenGonderilerDto> kullanicininBegenileri(String kullaniciEmail)
    {
        User kullanici = userRepository.findByEPosta(kullaniciEmail)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!"));

        List<BegenilenGonderiler> begenilenGonderilers = begenilenGonderilerRepository.findByKullaniciId(kullanici);
        if (begenilenGonderilers.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanıcının Beğendiği Gönderi Yok.");
        }
        return begenilenGonderilers.stream().map(begeni->new BegenilenGonderilerDto(begeni.getGonderiId().getGonderiId()))
                .toList();
    }
}
