package com.bitirmeproje.repository;

import com.bitirmeproje.dto.mesaj.KullanicininSonGelenMesajlari;
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

    @Query("""
    SELECT new com.bitirmeproje.dto.mesaj.KullanicininSonGelenMesajlari(
        m.mesajId,
        m.mesajIcerigi,
        m.mesajGonderilmeZamani,
        CASE\s
            WHEN m.mesajGonderenKullaniciId.kullaniciId = :currentUserId THEN m.mesajGonderilenKullaniciId.kullaniciTakmaAd
            ELSE m.mesajGonderenKullaniciId.kullaniciTakmaAd
        END,
        CASE\s
            WHEN m.mesajGonderenKullaniciId.kullaniciId = :currentUserId THEN m.mesajGonderilenKullaniciId.kullaniciProfilResmi
            ELSE m.mesajGonderenKullaniciId.kullaniciProfilResmi
        END,
        CASE\s
            WHEN m.mesajGonderenKullaniciId.kullaniciId = :currentUserId THEN m.mesajGonderilenKullaniciId.kullaniciId
            ELSE m.mesajGonderenKullaniciId.kullaniciId
        END
    )
    FROM Mesaj m
    WHERE m.mesajId IN (
        SELECT MAX(m2.mesajId)
        FROM Mesaj m2
        WHERE (m2.mesajGonderenKullaniciId.kullaniciId = :currentUserId AND m2.gonderenSildiMi = false)
        GROUP BY\s
            CASE WHEN m2.mesajGonderenKullaniciId.kullaniciId < m2.mesajGonderilenKullaniciId.kullaniciId\s
                 THEN m2.mesajGonderenKullaniciId.kullaniciId\s
                 ELSE m2.mesajGonderilenKullaniciId.kullaniciId\s
            END,
            CASE WHEN m2.mesajGonderenKullaniciId.kullaniciId > m2.mesajGonderilenKullaniciId.kullaniciId\s
                 THEN m2.mesajGonderenKullaniciId.kullaniciId\s
                 ELSE m2.mesajGonderilenKullaniciId.kullaniciId\s
            END
    )
    ORDER BY m.mesajGonderilmeZamani DESC
""")
    List<KullanicininSonGelenMesajlari> findSonKonusmalar(@Param("currentUserId") int currentUserId);

    @Modifying
    @Query("""
        UPDATE Mesaj m
        SET m.mesajOkunduMu = true
        WHERE m.mesajGonderenKullaniciId = :gonderen
        AND m.mesajGonderilenKullaniciId = :alici
        AND m.mesajOkunduMu = false
""")
        // Mesaj okundu mu bilgisini true yap
    void updateOkunduByGonderenAndAlici(@Param("gonderen") User gonderen, @Param("alici") User alici);

    @Query(value = """
    SELECT *
    FROM mesaj m
    WHERE m.mesaj_gonderen_kullanici_id = :mesajGonderenKullanici AND m.mesaj_gonderilen_kullanici_id = :mesajAtilanKullanici
    LIMIT 1
""", nativeQuery = true)
    Mesaj findKullanicilarArasindakiMesaj(int mesajGonderenKullanici, int mesajAtilanKullanici);

    @Modifying
    @Query(value = """
    UPDATE Mesaj m SET m.aliciSildiMi = TRUE
    WHERE m.mesajGonderenKullaniciId.kullaniciId = :mesajGonderenKullanici AND m.mesajGonderilenKullaniciId.kullaniciId = :mesajAtilanKullanici
""")
    void updateAliciSilindiMi(int mesajGonderenKullanici, int mesajAtilanKullanici);

    @Modifying
    @Query(value = """
    UPDATE Mesaj m SET m.gonderenSildiMi = TRUE
    WHERE m.mesajGonderenKullaniciId.kullaniciId = :mesajGonderenKullanici AND m.mesajGonderilenKullaniciId.kullaniciId = :mesajAtilanKullanici
""")
    void updateGonderenSilindiMi(int mesajGonderenKullanici, int mesajAtilanKullanici);
}