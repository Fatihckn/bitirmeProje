package com.bitirmeproje.helper.dto;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.model.YeniYorum;
import org.springframework.stereotype.Component;

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

//    @Override
//    public YeniYorum convertToEntity(YeniYorumDto dto) {
//        YeniYorum yeniYorum = new YeniYorum();
//        yeniYorum.setKullaniciId(new User(dto.getKullaniciId()));  // Sadece ID set ediliyor
//        yeniYorum.setGonderiId(new Gonderiler(dto.getGonderiId()));  // Sadece ID set ediliyor
//        yeniYorum.setYeniYorumIcerigi(dto.getYorumIcerigi());
//        yeniYorum.setYeniYorumOlusturulmaTarihi(LocalDate.now());
//        yeniYorum.setYeniYorumBegeniSayisi(0);
//        return yeniYorum;
//    }
}
