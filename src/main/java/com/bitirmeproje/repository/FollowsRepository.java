package com.bitirmeproje.repository;

import com.bitirmeproje.dto.follows.PopulerKullaniciDto;
import com.bitirmeproje.model.Follows;
import com.bitirmeproje.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, Integer> {

    //Bir kullanıcının başka bir kullanıcıyı takip edip etmediğini kontrol eder.
    @Query("SELECT f FROM Follows f WHERE f.takipEdenKullaniciId = :follower AND f.takipEdilenKullaniciId = :following")
    Optional<Follows> findByFollowerAndFollowing(@Param("follower") User follower, @Param("following") User following);

    //Belirtilen kullanıcının tüm takipçilerini (onu takip edenleri) getirir.
    @Query("SELECT f.takipEdenKullaniciId FROM Follows f WHERE f.takipEdilenKullaniciId.kullaniciId = :userId")
    List<User> findByFollowersUserId(@Param("userId") int userId);

    //Belirtilen kullanıcının takip ettiği tüm kullanıcıları getirir.
    @Query("SELECT f.takipEdilenKullaniciId FROM Follows f WHERE f.takipEdenKullaniciId.kullaniciId = :userId")
    List<User> findByFollowingUserId(@Param("userId") int userId);

    @Query("SELECT new com.bitirmeproje.dto.follows.PopulerKullaniciDto( k.kullaniciId, k.kullaniciTakmaAd, COUNT(f) ) " +
            "FROM Follows f " +
            "JOIN f.takipEdilenKullaniciId k " +
            "GROUP BY k.kullaniciId, k.kullaniciTakmaAd " +
            "ORDER BY COUNT(f) DESC")
    List<PopulerKullaniciDto> findMostFollowedUsers();



}
