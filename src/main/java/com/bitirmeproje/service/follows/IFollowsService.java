package com.bitirmeproje.service.follows;

import com.bitirmeproje.dto.follows.PopulerKullaniciDto;

import java.util.List;
import java.util.Map;

public interface IFollowsService {

    void followUser(int followingId);

    void unfollowUser(int followingId);

    Map<String, Object> getFollowers();

    Map<String, Object> getFollowing();

    List<PopulerKullaniciDto> populerKullanicilariGetir();
}
