package com.bitirmeproje.service.user;

import com.bitirmeproje.dto.user.ChangeEmailDto;
import com.bitirmeproje.dto.user.SifreDegistirDto;
import com.bitirmeproje.dto.user.UserDto;
import com.bitirmeproje.dto.user.UserUpdateDto;
import com.bitirmeproje.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    void passwordChange(SifreDegistirDto sifreDto);

//  void profilResmiGuncelle(User user, ProfilResmiGuncelleDto profilResmiGuncelle);

    List<UserDto> searchUsers(String query);

    void updateUser(UserUpdateDto userUpdateDto);

    UserDto findUserById();

    void passwordSave(User user, String yeniSifre);

    Optional<User> findByEposta (String ePosta);

    void changeUserEmail(ChangeEmailDto changeEmailDto);
}
