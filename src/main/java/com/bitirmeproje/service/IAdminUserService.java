package com.bitirmeproje.service;

import com.bitirmeproje.dto.UserAllDto;
import com.bitirmeproje.dto.UserUpdateDto;

import java.util.List;

public interface IAdminUserService {

    public List<UserAllDto> findAll();

    public List<UserAllDto> searchUsers(String query);

    public void updateUser(int userId, UserUpdateDto userUpdateDto);
}
