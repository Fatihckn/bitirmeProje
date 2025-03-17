package com.bitirmeproje.service.follows;

import com.bitirmeproje.dto.follows.PopulerKullaniciDto;
import com.bitirmeproje.repository.FollowsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowsService implements IFollowsService {
    private final FollowsRepository followsRepository;

    public FollowsService(FollowsRepository followsRepository)
    {
        this.followsRepository=followsRepository;
    }

    public List<PopulerKullaniciDto> populerKullanicilariGetir() {
        return followsRepository.findMostFollowedUsers();
    }
}
