package com.bitirmeproje.service.admin;

import com.bitirmeproje.dto.user.UserAllDto;
import com.bitirmeproje.dto.user.UserDto;

import java.util.List;

public interface IAdminUserService {

    List<UserAllDto> findAll();

    List<UserAllDto> searchUsers(String query);

    void updateUser(int userId, UserDto userDto);

    void deleteUserAccount(int kullaniciId);
}
