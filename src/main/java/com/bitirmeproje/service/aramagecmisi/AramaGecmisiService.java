package com.bitirmeproje.service.aramagecmisi;

import com.bitirmeproje.dto.aramagecmisi.AramaGecmisiDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.AramaGecmisi;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.AramaGecmisiRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AramaGecmisiService implements IAramaGecmisiService {
    private final AramaGecmisiRepository aramaGecmisiRepository;
    private final GetUserByToken getUserByToken;
    private final FindUser<Integer> findUser;

    public AramaGecmisiService(AramaGecmisiRepository aramaGecmisiRepository,
                               GetUserByToken getUserByToken, FindUser<Integer> findUser) {
        this.aramaGecmisiRepository = aramaGecmisiRepository;
        this.getUserByToken = getUserByToken;
        this.findUser = findUser;
    }

    // Kullanıcının yaptığı aramayı kaydet
    public void AramaKaydet(AramaGecmisiDto aramaGecmisiDto) {
        // Kullanıcıyı veritabanından çekiyoruz
        User kullanici = getUserByToken.getUser();

        findUser.findUser(aramaGecmisiDto.arananKullaniciId());

        // DTO'yu Entity'ye çeviriyoruz
        AramaGecmisi yeniArama = new AramaGecmisi();
        yeniArama.setArananKullaniciId(aramaGecmisiDto.arananKullaniciId());
        yeniArama.setAramaZamani(LocalDateTime.now()); // Arama zamanını sistem zamanı olarak al
        yeniArama.setKullaniciId(kullanici); // ManyToOne ilişkisini set ettik

        aramaGecmisiRepository.save(yeniArama);
    }

    // Kullanıcının tüm arama geçmişini getir (DTO formatında)
    public List<AramaGecmisiDto> getKullaniciAramaGecmisi() {

        User kullanici = getUserByToken.getUser();

        return aramaGecmisiRepository.findByKullaniciIdOrderByAramaZamaniDesc(kullanici)
                .stream()
                .map(arama -> new AramaGecmisiDto(
                        arama.getAramaGecmisiId(), // Arama geçmişi ID
                        arama.getKullaniciId().getKullaniciId(), // Arama içeriği
                        arama.getAramaZamani(), // Arama zamanı
                        findUser.findUser(arama.getArananKullaniciId()).getKullaniciProfilResmi(),
                        findUser.findUser(arama.getArananKullaniciId()).getKullaniciTakmaAd()
                ))
                .collect(Collectors.toList());
    }

    // Arama geçmişinden belirli bir kaydı sil
    public void deleteArama(int aramaGecmisiId) {
        // Kullanıcıyı JWT token içindeki email'den bul
        User kullanici = getUserByToken.getUser();

        // Arama geçmişini veritabanından çek
        AramaGecmisi aramaGecmisi = aramaGecmisiRepository.findAramaGecmisiByAramaGecmisiId(aramaGecmisiId);

        // Silme yetkisi var mı kontrol et (Arama geçmişi bu kullanıcıya mı ait?)!!! Bu kısımlar değiştirilebilir şuanlık kontrol kalsın.
        if (!aramaGecmisi.getKullaniciId().equals(kullanici)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Bu gönderiyi silme yetkiniz yok.");
        }

        // Arama geçmişini sil
        aramaGecmisiRepository.deleteById(aramaGecmisiId);
    }

    @Transactional
    public void deleteTumAramaGecmisi(){aramaGecmisiRepository.deleteByKullaniciId( getUserByToken.getUser());}
}
