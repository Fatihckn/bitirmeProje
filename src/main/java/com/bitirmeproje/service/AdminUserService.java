package com.bitirmeproje.service;

import com.bitirmeproje.dto.user.UserAllDto;
import com.bitirmeproje.dto.user.UserUpdateDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class AdminUserService implements IAdminUserService {

    private final UserRepository userRepository;
    private final FindUser<Integer> findUser;

    AdminUserService(UserRepository userRepository,
                     @Qualifier("findUserById") FindUser<Integer> findUser) {
        this.userRepository = userRepository;
        this.findUser = findUser;
    }

    // Kullanıcının bütün bilgilerini getir(Admin API'si için)
    public List<UserAllDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserAllDto(
                        user.getKullaniciId(),
                        user.getKullaniciTakmaAd(),
                        user.getePosta(),
                        user.getKullaniciBio(),
                        user.getKullaniciProfilResmi(),
                        user.getKullaniciTelefonNo(),
                        user.getKullaniciDogumTarihi(),
                        user.getKullaniciUyeOlmaTarihi(),
                        user.getKullaniciRole()
                ))
                .toList();
    }

    // Aradığın kullanıcının bilgileri getiriliyor
    public List<UserAllDto> searchUsers(String query) {
        List<User> users = userRepository.searchByQuery(query);

        if (users.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanıcı bulunamadı");
        }

        return users.stream()
                .map(user -> new UserAllDto(
                        user.getKullaniciId(),
                        user.getKullaniciTakmaAd(),
                        user.getePosta(),
                        user.getKullaniciBio(),
                        user.getKullaniciProfilResmi(),
                        user.getKullaniciTelefonNo(),
                        user.getKullaniciDogumTarihi(),
                        user.getKullaniciUyeOlmaTarihi(),
                        user.getKullaniciRole()
                ))
                .toList();
    }

    // İstediğin kullanıcının bilgilerini güncelle.
    public void updateUser(int userId, UserUpdateDto userUpdateDto) {
        User user = findUser.findUser(userId);

        updateField(userUpdateDto.getKullaniciTakmaAd(), user::setKullaniciTakmaAd);
        updateField(userUpdateDto.getKullaniciBio(), user::setKullaniciBio);
        updateField(userUpdateDto.getKullaniciTelefonNo(), user::setKullaniciTelefonNo);
        updateField(userUpdateDto.getKullaniciProfilResmi(), user::setKullaniciProfilResmi);

        userRepository.save(user);
    }

    private void updateField(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }
}
