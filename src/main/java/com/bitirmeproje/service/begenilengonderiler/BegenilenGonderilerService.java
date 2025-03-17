package com.bitirmeproje.service.begenilengonderiler;

import com.bitirmeproje.dto.gonderiler.GonderilerAllDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.BegenilenGonderiler;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.BegenilenGonderilerRepository;
import com.bitirmeproje.repository.GonderilerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bitirmeproje.dto.begenilengonderiler.BegenilenGonderilerDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BegenilenGonderilerService implements IBegenilenGonderilerService {

    private final BegenilenGonderilerRepository begenilenGonderilerRepository;
    private final GonderilerRepository gonderilerRepository;
    private final GetUserByToken getUserByToken;

    public BegenilenGonderilerService(BegenilenGonderilerRepository begenilenGonderilerRepository,
                                      GonderilerRepository gonderilerRepository,
                                      GetUserByToken getUserByToken)
    {
        this.begenilenGonderilerRepository=begenilenGonderilerRepository;
        this.gonderilerRepository=gonderilerRepository;
        this.getUserByToken=getUserByToken;
    }

    // Gönderi Beğenme
    public void begeniEkle(int gonderiId) {
        // Kullanıcıyı email ile bul
        User kullanici = getUserByToken.getUser();

        // Gonderiler nesnesini oluşturan metod çağırılıyor
        Gonderiler gonderi = getGonderiById(gonderiId);

        // Kullanıcının bu gönderiyi daha önce beğenip beğenmediğini kontrol et
        if (begenilenGonderilerRepository.existsByGonderiIdAndKullaniciId(gonderi, kullanici)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu gönderiyi zaten beğendiniz!");
        }

        // Yeni beğeni kaydı oluştur
        BegenilenGonderiler begeni = createBegeni(gonderi, kullanici);

        gonderilerRepository.incrementBegeniSayisi(gonderi.getGonderiId());

        // Kaydet
        begenilenGonderilerRepository.save(begeni);
    }

    // Gönderiden beğeniyi kaldır (Repository'de delete işlemi Entity manager üzerinden çalıştığı için transaction kontrölünü eklememiz lazım)
    @Transactional
    public void begeniKaldir(int gonderiId) {

        // Kullanıcıyı email ile bul
        User kullanici = getUserByToken.getUser();

        Gonderiler gonderi = getGonderiById(gonderiId);

        // Kullanıcının bu gönderiyi daha önce beğenip beğenmediğini kontrol et
        if (!begenilenGonderilerRepository.existsByGonderiIdAndKullaniciId(gonderi, kullanici)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu gönderiyi zaten beğenmiyorsunuz!");
        }

        gonderilerRepository.decreaseBegeniSayisi(gonderi.getGonderiId());
        begenilenGonderilerRepository.deleteByGonderiIdAndKullaniciId(gonderi, kullanici);
    }

    // Gönderinin beğeni sayısı
    public int gonderiBegeniSayisi(int gonderiId) {

        Gonderiler gonderi = getGonderiById(gonderiId);
        return gonderi.getGonderiBegeniSayisi();
    }

    // Bütün gönderilerin beğeni sayısı
    public Map<Integer, Integer> gonderiBegeniSayisiAll() {
        List<GonderilerAllDto> allGonderiler = begenilenGonderilerRepository.findAllBegeniler();

        if(allGonderiler.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Begenilen gonderi bulunamadi");
        }

        // DTO listesini Map'e çeviriyoruz
        return  allGonderiler.stream()
                .collect(Collectors.toMap(
                        GonderilerAllDto::getGonderi_id,   // Key = gonderi_id
                        GonderilerAllDto::getGonderi_begeni_sayisi // Value = gonderi_begeni_sayisi
                ));
    }


    //belirli bir kullanıcının beğendiği gönderiler
    public List<BegenilenGonderilerDto> kullanicininBegenileri()
    {
        User kullanici = getUserByToken.getUser();

        List<BegenilenGonderiler> begenilenGonderilers = begenilenGonderilerRepository.findByKullaniciId(kullanici);
        if (begenilenGonderilers.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanıcının Beğendiği Gönderi Yok.");
        }
        return begenilenGonderilers.stream().map(begeni->new BegenilenGonderilerDto(begeni.getGonderiId().getGonderiId(), begeni.getBegenilenGonderilerId(), begeni.getBegenmeZamani()))
                .toList();
    }

    // Gönderi ID ile gönderi nesnesi oluşturuluyor.
    private Gonderiler getGonderiById(int gonderiId) {
        Gonderiler gonderi = gonderilerRepository.findByGonderiId(gonderiId);

        if(gonderi == null) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Gonderi bulunamadi");
        }
        return gonderi;
    }

    // Beğenilen gönderi nesnesi oluşturulup field'larına set ediliyor.
    private BegenilenGonderiler createBegeni(Gonderiler gonderi, User kullanici) {
        BegenilenGonderiler begeni = new BegenilenGonderiler();
        begeni.setGonderiId(gonderi);
        begeni.setKullaniciId(kullanici);
        return begeni;
    }
}
