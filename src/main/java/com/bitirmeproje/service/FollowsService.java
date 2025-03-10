package com.bitirmeproje.service;

import com.bitirmeproje.dto.FollowsDto;
import com.bitirmeproje.dto.PopulerKullaniciDto;
import com.bitirmeproje.model.Follows;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.FollowsRepository;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class FollowsService {
    private final FollowsRepository followsRepository;

    public FollowsService(FollowsRepository followsRepository)
    {
        this.followsRepository=followsRepository;
    }

    public List<PopulerKullaniciDto> populerKullanicilariGetir() {
        return followsRepository.findMostFollowedUsers();
    }
}
