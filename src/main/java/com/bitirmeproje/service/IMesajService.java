package com.bitirmeproje.service;

import com.bitirmeproje.dto.mesaj.MesajCreateDto;
import com.bitirmeproje.dto.mesaj.MesajDto;
import com.bitirmeproje.dto.mesaj.MesajUpdateDto;

import java.util.List;
import java.util.Optional;

public interface IMesajService {

    // Yeni mesaj gönderme
    MesajDto mesajGonder(MesajCreateDto mesajCreateDto);

    // Kullanıcının gelen mesajlarını listele
    List<MesajDto> gelenMesajlariListele(int kullaniciId);

    // Kullanıcının gönderdiği mesajları listele
    List<MesajDto> gonderilenMesajlariListele(int kullaniciId);

    // ID'ye göre mesaj getir
    Optional<MesajDto> mesajGetir(int mesajId);

    // İki kullanıcı arasındaki mesajları getir (sohbet geçmişi)
    List<MesajDto> sohbetGecmisiGetir(int kullanici1Id, int kullanici2Id);

    // Mesaj içeriğini güncelle
    void mesajGuncelle(int mesajId, MesajUpdateDto mesajUpdateDto);

    // Belirli bir mesajı sil
    void mesajSil(int mesajId);

    // Belirli bir kullanıcı ile olan tüm mesajları sil
    void tumMesajlariSil(int kullanici1Id, int kullanici2Id);
}
