package com.bitirmeproje.service.user;

import com.bitirmeproje.dto.user.*;
import com.bitirmeproje.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    void passwordChange(SifreDegistirDto sifreDto);

  void profilResmiGuncelle(ProfilResmiGuncelleDto profilResmiGuncelle);

    List<UserDto> searchUsers(String query);

    void updateUser(UserDto userUpdateDto);

    UserDto findUserById();

    void passwordSave(User user, String yeniSifre);

    Optional<User> findByEposta (String ePosta);

    void changeUserEmail(ChangeEmailDto changeEmailDto);
}
