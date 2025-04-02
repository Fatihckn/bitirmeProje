package com.bitirmeproje.service.yeniyorumbegeniler;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import com.bitirmeproje.model.YeniYorumBegeniler;
import com.bitirmeproje.repository.YeniYorumBegenilerRepository;
import com.bitirmeproje.repository.YeniYorumRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class YeniYorumBegenilerService implements IYeniYorumBegenilerService{
    private final YeniYorumBegenilerRepository yeniYorumBegenilerRepository;
    private final YeniYorumRepository yeniYorumRepository;
    private final IEntityDtoConverter<YeniYorum,YeniYorumDto> iEntityDtoConverter;
    private final GetUserByToken getUserByToken;

    public YeniYorumBegenilerService(YeniYorumBegenilerRepository yeniYorumBegenilerRepository,
                                     YeniYorumRepository yeniYorumRepository,
                                     @Qualifier("yeniYorumConverter") IEntityDtoConverter<YeniYorum, YeniYorumDto> iEntityDtoConverter,
                                     GetUserByToken getUserByToken) {
        this.yeniYorumBegenilerRepository = yeniYorumBegenilerRepository;
        this.yeniYorumRepository = yeniYorumRepository;
        this.iEntityDtoConverter = iEntityDtoConverter;
        this.getUserByToken = getUserByToken;
    }

    @Transactional
    public void yorumBegen(int yorumId) {
        User user = getUserByToken.getUser();

        YeniYorum yorum = getYeniYorum(yorumId);

        // Kullanıcının daha önce bu yorumu beğenip beğenmediğini kontrol et
        boolean begeniVar = yeniYorumBegenilerRepository.existsByYeniYorumAndKullanici(yorum, user);

        if (begeniVar) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Zaten bu yorumu beğendiniz.");
        }

        // Yeni beğeni kaydı oluştur
        YeniYorumBegeniler yeniBegeni = new YeniYorumBegeniler();
        yeniBegeni.setYeniYorum(yorum);
        yeniBegeni.setKullanici(user);

        yeniYorumBegenilerRepository.save(yeniBegeni);

        // Yorumun beğeni sayısını artır
        yorum.setYeniYorumBegeniSayisi(yorum.getYeniYorumBegeniSayisi() + 1);
        yeniYorumRepository.save(yorum);
    }

    public void yorumBegeniCek(int yorumId) {
        User user = getUserByToken.getUser();

        YeniYorum yorum = getYeniYorum(yorumId);

        // Kullanıcının bu yoruma yaptığı beğeniyi kontrol et
        Optional<YeniYorumBegeniler> begeni = yeniYorumBegenilerRepository.findByYeniYorumAndKullanici(yorum, user);

        if (begeni.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu yorumu zaten beğenmemişsiniz.");
        }

        // Beğeni kaydını sil
        yeniYorumBegenilerRepository.delete(begeni.get());

        // Yorumun beğeni sayısını azalt
        yorum.setYeniYorumBegeniSayisi(Math.max(0, yorum.getYeniYorumBegeniSayisi() - 1));
        yeniYorumRepository.save(yorum);
    }

    public int getBegeniSayisi(int yorumId) {
        YeniYorum yeniYorum = getYeniYorum(yorumId);

        return yeniYorumBegenilerRepository.countByYeniYorum(yeniYorum);
    }

    public List<YeniYorumDto> getBegenilenYorumlar(int kullaniciId) {
        List<YeniYorum> begenilenYorumlar = yeniYorumBegenilerRepository
                .findByKullanici_KullaniciId(kullaniciId)
                .stream()
                .map(YeniYorumBegeniler::getYeniYorum) // Beğenilen yorumları çekiyoruz
                .toList();

        return begenilenYorumlar.stream()
                .map(iEntityDtoConverter::convertToDTO) // DTO'ya çeviriyoruz
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
