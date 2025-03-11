package com.bitirmeproje.service;

import com.bitirmeproje.dto.user.ChangeEmailDto;
import com.bitirmeproje.dto.user.SifreDegistirDto;
import com.bitirmeproje.dto.user.UserDto;
import com.bitirmeproje.dto.user.UserUpdateDto;
import com.bitirmeproje.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public void passwordChange(User user, SifreDegistirDto sifreDto);

//  public void profilResmiGuncelle(User user, ProfilResmiGuncelleDto profilResmiGuncelle);

    public List<UserDto> searchUsers(String query);

    public void followUser(User follower, int followingId);

    public void unfollowUser(User follower, int followingId);

    public List<UserDto> getFollowers(int userId);

    public List<UserDto> getFollowing(int userId);

    public void updateUser(int userId, UserUpdateDto userUpdateDto);

    public List<UserDto> findUserById(int id);

    public void passwordSave(User user, String yeniSifre);

    public Optional<User> findByEposta (String ePosta);

    public void changeUserEmail(int userId, ChangeEmailDto changeEmailDto);
}
