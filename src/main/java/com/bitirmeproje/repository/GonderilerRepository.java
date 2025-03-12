package com.bitirmeproje.repository;

import com.bitirmeproje.dto.gonderiler.GonderiResponseDto;
import com.bitirmeproje.model.Gonderiler;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GonderilerRepository extends JpaRepository<Gonderiler, Integer> {

    // Belirli bir kullanıcının tüm gönderilerini getir
    List<Gonderiler> findByKullaniciId_KullaniciId(int kullaniciId);

    @Query("SELECT new com.bitirmeproje.dto.gonderiler.GonderiResponseDto(" +
            "g.gonderiId, " +
            "g.gonderiIcerigi, " +
            "g.gonderiTarihi, " +
            "COALESCE(g.gonderiBegeniSayisi, 0), " + // NULL değerleri 0 yap
            "COALESCE(g.kullaniciId.kullaniciTakmaAd, 'Bilinmeyen Kullanıcı')" + // NULL önleme
            ") FROM Gonderiler g " +
            "LEFT JOIN g.kullaniciId u " + // Kullanıcı ilişkisinin boş olup olmadığını kontrol et
            "ORDER BY g.gonderiBegeniSayisi DESC")
    List<GonderiResponseDto> findPopularPosts();



    // Gönderiyi ID'ye göre bul
    Optional<Gonderiler> findById(int id);

    // Belirli bir kullanıcıya ait tüm gönderileri getir
    List<Gonderiler> findByKullaniciId_KullaniciIdAndGonderiIcerigiContaining(int kullaniciId, String gonderiIcerigi);

    // Belirli bir gönderiyi ID ile getir
    Optional<Gonderiler> findByGonderiId(int gonderiId);

    // Belirli bir kullanıcının belirli bir içeriğe sahip gönderilerini getir
    List<Gonderiler> findByKullaniciIdAndGonderiIcerigiContaining(User kullaniciId, String gonderiIcerigi);
}
