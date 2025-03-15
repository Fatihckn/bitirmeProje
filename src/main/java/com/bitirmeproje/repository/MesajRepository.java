package com.bitirmeproje.repository;

import com.bitirmeproje.model.Mesaj;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesajRepository extends JpaRepository<Mesaj, Integer> {

    // Belirli bir kullanıcının aldığı mesajları listele
    List<Mesaj> findByMesajGonderilenKullaniciId(User mesajGonderilenKullaniciId);

    // Belirli bir kullanıcının gönderdiği mesajları listele
    List<Mesaj> findByMesajGonderenKullaniciId(User mesajGonderenKullaniciId);

    // İki kullanıcı arasındaki tüm mesajları getir (Sohbet geçmişi)
    @Query("SELECT m FROM Mesaj m WHERE " +
            "(m.mesajGonderenKullaniciId = :kullanici1 AND m.mesajGonderilenKullaniciId = :kullanici2) " +
            "OR (m.mesajGonderenKullaniciId = :kullanici2 AND m.mesajGonderilenKullaniciId = :kullanici1) " +
            "ORDER BY m.mesajGonderilmeZamani ASC")
    List<Mesaj> findSohbetGecmisi(@Param("kullanici1") User kullanici1, @Param("kullanici2") User kullanici2);

    // ID ile mesajı getir
    Mesaj findByMesajId(int mesajId);

    // Belirli bir kullanıcıyla olan tüm mesajları sil
    @Modifying
    @Query("DELETE FROM Mesaj m WHERE (m.mesajGonderenKullaniciId = :k1 AND m.mesajGonderilenKullaniciId = :k2) OR (m.mesajGonderenKullaniciId = :k2 AND m.mesajGonderilenKullaniciId = :k1)")
    void deleteByMesajGonderenKullaniciIdOrMesajGonderilenKullaniciId(@Param("k1") User kullanici1, @Param("k2") User kullanici2);
}