package com.bitirmeproje.service.yeniyorum;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.dto.yeniyorum.YeniYorumDtoWithBegenildiMi;
import com.bitirmeproje.dto.yeniyorum.YeniYorumDtoWithTakmaAdPhoto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.dto.YeniYorumConverter;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.repository.YeniYorumRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class YeniYorumService implements IYeniYorumService{

    private final YeniYorumRepository yeniYorumRepository;
    private final GonderilerRepository gonderilerRepository;
    private final IEntityDtoConverter<YeniYorum,YeniYorumDto> iEntityDtoConverter;
    private final GetUserByToken getUserByToken;
    private final YeniYorumConverter yeniYorumConverter;
    private final FindUser<String> findUser;


    public YeniYorumService(YeniYorumRepository yeniYorumRepository,
                            GonderilerRepository gonderilerRepository,
                            @Qualifier("yeniYorumConverter") IEntityDtoConverter<YeniYorum, YeniYorumDto> iEntityDtoConverter,
                            GetUserByToken getUserByToken, YeniYorumConverter yeniYorumConverter,
                            @Qualifier("findUserByEmail") FindUser<String> findUser) {
        this.yeniYorumRepository = yeniYorumRepository;
        this.gonderilerRepository = gonderilerRepository;
        this.iEntityDtoConverter = iEntityDtoConverter;
        this.getUserByToken = getUserByToken;
        this.yeniYorumConverter = yeniYorumConverter;
        this.findUser = findUser;
    }

    public YeniYorum yeniYorumEkle(YeniYorumDto yeniYorumDto) {
        User user = getUserByToken.getUser();

        Gonderiler gonderi = gonderilerRepository.findByGonderiId(yeniYorumDto.getGonderiId());
        if (gonderi == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Gönderi bulunamadı.");
        }

        YeniYorum yeniYorum = new YeniYorum();
        yeniYorum.setKullaniciId(user);
        yeniYorum.setGonderiId(gonderi);
        yeniYorum.setYeniYorumIcerigi(yeniYorumDto.getYorumIcerigi());
        yeniYorum.setYeniYorumOlusturulmaTarihi(LocalDateTime.now());
        yeniYorum.setYeniYorumBegeniSayisi(0);

        // Eğer parent yorum ID varsa alt yorum olarak ata
        if (yeniYorumDto.getParentYorumId() != null) {
            YeniYorum parentYorum = getYeniYorum(yeniYorumDto.getParentYorumId());
            yeniYorum.setParentYorum(parentYorum);
        }

        return yeniYorumRepository.save(yeniYorum);
    }

    public List<YeniYorumDto> getYorumlarByGonderiId(int gonderiId) {
        List<YeniYorum> yorumlar = yeniYorumRepository.findByGonderiId_GonderiIdOrderByYeniYorumOlusturulmaTarihiDesc(gonderiId);
        return yorumlar.stream().map(iEntityDtoConverter::convertToDTO).collect(Collectors.toList());
    }

    // **Yeni eklenen API**: Belirli bir kullanıcının yaptığı yorumları getir
    public List<YeniYorumDto> getYorumlarByKullaniciId(int kullaniciId) {
        List<YeniYorum> yorumlar = yeniYorumRepository.findByKullaniciId_KullaniciId(kullaniciId);

        return yorumlar.stream()
                .map(iEntityDtoConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public void yorumuSil(int yorumId) {
        YeniYorum yorum = getYeniYorum(yorumId);

        // Başka birinin yorumunu silemesin.
        if(yorum.getKullaniciId().getKullaniciId() != getUserByToken.getUser().getKullaniciId()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Yorum bulunamadi");
        }

        yeniYorumRepository.deleteById(yorumId);
    }

    public YeniYorumDtoWithBegenildiMi yorumaYanitEkle(int yorumId, YeniYorumDto yeniYorumDto) {
        User user = getUserByToken.getUser();

        YeniYorum parentYorum = getYeniYorum(yorumId);

        YeniYorum yeniYorum = yeniYorumConverter.convertToEntity(yeniYorumDto, user, parentYorum);

        YeniYorum yeniYorumYanit =  yeniYorumRepository.save(yeniYorum);

        YeniYorumDtoWithBegenildiMi yeniYorumDtoWithBegenildiMi = new YeniYorumDtoWithBegenildiMi();
        yeniYorumDtoWithBegenildiMi.setYorumId(yeniYorumYanit.getYorumId());
        yeniYorumDtoWithBegenildiMi.setYorumYapanResim(user.getKullaniciProfilResmi());
        yeniYorumDtoWithBegenildiMi.setYorumYapanTakmaAd(user.getKullaniciTakmaAd());
        yeniYorumDtoWithBegenildiMi.setYeniYorumIcerigi(yeniYorumYanit.getYeniYorumIcerigi());
        yeniYorumDtoWithBegenildiMi.setYeniYorumBegeniSayisi(yeniYorumYanit.getYeniYorumBegeniSayisi());
        yeniYorumDtoWithBegenildiMi.setYeniYorumOlusturulmaTarihi(yeniYorumYanit.getYeniYorumOlusturulmaTarihi());
        yeniYorumDtoWithBegenildiMi.setYorumuBegendimMi(false);
        return yeniYorumDtoWithBegenildiMi;
    }

    public List<YeniYorumDtoWithTakmaAdPhoto> getYanitlarByYorumId(int yorumId) {
        List<YeniYorum> yanitlar = yeniYorumRepository.findByParentYorum_YorumIdOrderByYeniYorumOlusturulmaTarihiAsc(yorumId);

        return yanitlar.stream()
                .map(y -> {
                    YeniYorumDtoWithTakmaAdPhoto dto = new YeniYorumDtoWithTakmaAdPhoto();
                    dto.setYeniYorumId(y.getYorumId());
                    dto.setYorumIcerigi(y.getYeniYorumIcerigi());
                    dto.setParentYorumId(y.getParentYorum().getYorumId());
                    dto.setGonderiId(y.getGonderiId().getGonderiId());
                    dto.setKullaniciFoto(findUser.findUser(y.getKullaniciId().getePosta()).getKullaniciProfilResmi());
                    dto.setKullaniciId(y.getKullaniciId().getKullaniciId());
                    dto.setKullaniciTakmaAd(findUser.findUser(y.getKullaniciId().getePosta()).getKullaniciTakmaAd());
                    dto.setYeniYorumTarihi(y.getYeniYorumOlusturulmaTarihi());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private YeniYorum getYeniYorum(int yorumId) {
        YeniYorum yeniYorum = yeniYorumRepository.findByYorumId(yorumId);
        if (yeniYorum == null) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Yorum bulunamadi");
        }
        return yeniYorum;
    }
}