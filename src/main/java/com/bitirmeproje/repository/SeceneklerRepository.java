package com.bitirmeproje.repository;

import com.bitirmeproje.dto.secenekler.SeceneklerDtoWithCevapSayisi;
import com.bitirmeproje.model.Secenekler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeceneklerRepository extends JpaRepository<Secenekler, Integer> {

    @Query("""
    SELECT NEW com.bitirmeproje.dto.secenekler.SeceneklerDtoWithCevapSayisi(
    COUNT(c.cevapId),
    s.secenekId,
    s.anketId.anketId,
    s.secenekMetni
    )
    FROM Secenekler s
    LEFT JOIN Cevaplar c ON c.secenekId.secenekId = s.secenekId
    WHERE s.anketId.anketId =:anketId
    GROUP BY s.secenekId, s.anketId.anketId, s.secenekMetni
""")
    List<SeceneklerDtoWithCevapSayisi> getSeceneklerByAnketId(int anketId);

    Secenekler findSeceneklerBySecenekId(int secenekId);
}
