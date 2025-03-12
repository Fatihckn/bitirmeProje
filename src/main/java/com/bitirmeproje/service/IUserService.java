package com.bitirmeproje.service;

import com.bitirmeproje.dto.user.ChangeEmailDto;
import com.bitirmeproje.dto.user.SifreDegistirDto;
import com.bitirmeproje.dto.user.UserDto;
import com.bitirmeproje.dto.user.UserUpdateDto;
import com.bitirmeproje.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    void passwordChange(User user, SifreDegistirDto sifreDto);

//  void profilResmiGuncelle(User user, ProfilResmiGuncelleDto profilResmiGuncelle);

    List<UserDto> searchUsers(String query);

    void followUser(User follower, int followingId);

    void unfollowUser(User follower, int followingId);

    List<UserDto> getFollowers(int userId);

    List<UserDto> getFollowing(int userId);

    void updateUser(int userId, UserUpdateDto userUpdateDto);

    UserDto findUserById();

    void passwordSave(User user, String yeniSifre);

    Optional<User> findByEposta (String ePosta);

    void changeUserEmail(int userId, ChangeEmailDto changeEmailDto);
}
