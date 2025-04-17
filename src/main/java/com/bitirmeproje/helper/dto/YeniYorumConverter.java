package com.bitirmeproje.helper.dto;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.model.User;
import com.bitirmeproje.model.YeniYorum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class YeniYorumConverter implements IEntityDtoConverter<YeniYorum, YeniYorumDto> {

    @Override
    public YeniYorumDto convertToDTO(YeniYorum yorum) {
        YeniYorumDto dto = new YeniYorumDto();
        dto.setYeniYorumId(yorum.getYorumId());
        dto.setKullaniciId(yorum.getKullaniciId().getKullaniciId());
        dto.setGonderiId(yorum.getGonderiId().getGonderiId());
        dto.setYorumIcerigi(yorum.getYeniYorumIcerigi());
        dto.setParentYorumId(yorum.getParentYorum() != null ? yorum.getParentYorum().getYorumId() : null);
        return dto;
    }

    public YeniYorum convertToEntity(YeniYorumDto dto, User user, YeniYorum parentYorum) {
        YeniYorum yeniYorum = new YeniYorum();
        yeniYorum.setKullaniciId(user);  // Sadece ID set ediliyor
        yeniYorum.setGonderiId(parentYorum.getGonderiId());  // Sadece ID set ediliyor
        yeniYorum.setYeniYorumIcerigi(dto.getYorumIcerigi());
        yeniYorum.setYeniYorumOlusturulmaTarihi(LocalDateTime.now());
        yeniYorum.setYeniYorumBegeniSayisi(0);
        yeniYorum.setParentYorum(parentYorum); // Parent yorum set ediliyor
        return yeniYorum;
    }
}
