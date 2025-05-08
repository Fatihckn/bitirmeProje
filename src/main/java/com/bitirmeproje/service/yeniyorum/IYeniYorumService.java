package com.bitirmeproje.service.yeniyorum;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;
import com.bitirmeproje.model.YeniYorum;

import java.util.List;

public interface IYeniYorumService {

    YeniYorum yeniYorumEkle(YeniYorumDto yeniYorumDto);

    List<YeniYorumDto> getYorumlarByGonderiId(int gonderiId);

    List<YeniYorumDto> getYorumlarByKullaniciId(int kullaniciId);

    void yorumuSil(int yorumId);

    YeniYorum yorumaYanitEkle(int yorumId, YeniYorumDto yeniYorumDto);

    List<YeniYorumDto> getYanitlarByYorumId(int yorumId);


}
