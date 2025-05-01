package com.bitirmeproje.service.user;

import com.bitirmeproje.dto.user.*;
import com.bitirmeproje.model.User;

import java.util.List;

public interface IUserService {

    void passwordChange(SifreDegistirDto sifreDto);

    void profilResmiGuncelle(ProfilResmiGuncelleDto profilResmiGuncelle);

    void sifreSifirla(String email);

    void sifreDogrula(String email, String otp);

    void yeniSifreBelirle(String email, String yeniSifre);

    List<UserDto> searchUsers(String query);

    void updateUser(UserDto userUpdateDto);

    UserDto findUserById();

    UserGonderilerDto findUserByIdAranan(String takmaAd);

    void passwordSave(User user, String yeniSifre);

    void changeUserEmail(ChangeEmailDto changeEmailDto);

    void deleteUserAccount(String otp);

    void validation();

    void validationForEmail(ChangeEmailDto2 changeEmailDto);
}
