package com.bitirmeproje.service.gonderiler;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.GonderilerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GonderilerService implements IGonderilerService {

    private final GonderilerRepository gonderilerRepository;
    private final IEntityDtoConverter<Gonderiler, GonderiDto> entityDtoConvert;
    private final GetUserByToken getUserByToken;

    public GonderilerService(GonderilerRepository gonderilerRepository,
                             IEntityDtoConverter<Gonderiler, GonderiDto> entityDtoConvert,
                             GetUserByToken getUserByToken) {
        this.gonderilerRepository = gonderilerRepository;
        this.entityDtoConvert = entityDtoConvert;
        this.getUserByToken = getUserByToken;
    }

    // Belirli bir kullanıcının tüm gönderilerini getir
    public List<GonderiDto> kullaniciGonderileriniGetir() {
        User user = getUserByToken.getUser();

        List<Gonderiler> gonderilerList = gonderilerRepository.findByKullaniciId_KullaniciId(user.getKullaniciId());

        return gonderilerList.stream()
                .map(entityDtoConvert::convertToDTO)
                .toList();
    }

    // Yeni gönderi ekle
    public void yeniGonderiEkle(GonderiDto gonderiDto) {

        Gonderiler gonderi = entityDtoConvert.convertToEntity(gonderiDto);
        gonderilerRepository.save(gonderi);
    }

    // En popüler gönderileri getir
    public List<GonderiDto> populerGonderileriGetir() {
        return gonderilerRepository.findPopularPosts();
    }

    // Gönderiyi sil
    public void gonderiSil(int gonderiId) {
        Gonderiler gonderi = findGonderi(gonderiId);

        if(gonderi.getKullaniciId().getKullaniciId() != (getUserByToken.getUser().getKullaniciId())) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Gonderi bulunamadi");
        }

        gonderilerRepository.deleteById(gonderi.getGonderiId());
    }

    // Gönderi içeriğini güncelle
    public void gonderiGuncelle(int gonderiId, String yeniIcerik) {
        Gonderiler gonderi = findGonderi(gonderiId);

        if (yeniIcerik.isEmpty()){
            throw new CustomException(HttpStatus.NOT_FOUND,"İçerik boş olamaz");
        }
        User user = getUserByToken.getUser();

        if(gonderi.getKullaniciId().getKullaniciId() != user.getKullaniciId()){
            throw new CustomException(HttpStatus.NOT_FOUND,"Gönderi Bulunamadi");
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
