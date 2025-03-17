package com.bitirmeproje.service.yeniyorumbegeniler;

import com.bitirmeproje.dto.yeniyorum.YeniYorumDto;

import java.util.List;

public interface IYeniYorumBegenilerService {

    void yorumBegen(int yorumId);

    void yorumBegeniCek(int yorumId);

    int getBegeniSayisi(int yorumId);

    List<YeniYorumDto> getBegenilenYorumlar(int kullaniciId);
}
