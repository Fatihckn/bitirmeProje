package com.bitirmeproje.repository;

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

    @Query("SELECT f FROM Follows f WHERE f.takipEdenKullaniciId = :follower AND f.takipEdilenKullaniciId = :following")
    Optional<Follows> findByFollowerAndFollowing(@Param("follower") User follower, @Param("following") User following);

    @Query("SELECT f.takipEdenKullaniciId FROM Follows f WHERE f.takipEdilenKullaniciId.kullaniciId = :userId")
    List<User> findByFollowersUserId(@Param("userId") int userId);

    @Query("SELECT f.takipEdilenKullaniciId FROM Follows f WHERE f.takipEdenKullaniciId.kullaniciId = :userId")
    List<User> findByFollowingUserId(@Param("userId") int userId);

}
