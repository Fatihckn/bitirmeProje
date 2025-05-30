package com.bitirmeproje.service.mesaj;

import com.bitirmeproje.dto.mesaj.*;

import java.util.List;
import java.util.Optional;

public interface IMesajService {

    // Yeni mesaj gönderme
    MesajAtmaDto mesajGonder(MesajCreateDto mesajCreateDto);

    // Kullanıcının gelen mesajlarını listele
    List<MesajDto> gelenMesajlariListele();

    // Kullanıcının gönderdiği mesajları listele
    List<MesajDto> gonderilenMesajlariListele();

    // ID'ye göre mesaj getir
    Optional<MesajDto> mesajGetir(int mesajId);

    // İki kullanıcı arasındaki mesajları getir (sohbet geçmişi)
    List<MesajSohbetGecmisiGetirDto> sohbetGecmisiGetir(int kullaniciId);

    // Mesaj içeriğini güncelle
    void mesajGuncelle(int mesajId, MesajDto mesajDto);

    // Belirli bir mesajı sil
    void mesajSil(int mesajId);

    // Belirli bir kullanıcı ile olan tüm mesajları sil
    void tumMesajlariSil(int kullaniciId);

    List<KullanicininSonGelenMesajlari> getKullanicininSonGelenMesajlari();

    void updateMesajOkunduMu(int kullaniciId);
}
