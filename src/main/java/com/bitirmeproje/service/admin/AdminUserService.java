package com.bitirmeproje.service.admin;

import com.bitirmeproje.dto.user.UserAllDto;
import com.bitirmeproje.dto.user.UserDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
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
    private final IEntityDtoConverter<User, UserAllDto> converter;

    AdminUserService(UserRepository userRepository,
                     @Qualifier("findUserById") FindUser<Integer> findUser,
                     IEntityDtoConverter<User, UserAllDto> converter) {
        this.userRepository = userRepository;
        this.findUser = findUser;
        this.converter = converter;
    }

    // Kullanıcının bütün bilgilerini getir(Admin API'si için)
    public List<UserAllDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(converter::convertToDTO)
                .toList();
    }

    // Aradığın kullanıcının bilgileri getiriliyor
    public List<UserAllDto> searchUsers(String query) {
        List<User> users = userRepository.searchByQuery(query);

        if (users.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanıcı bulunamadı");
        }

        return users.stream()
                .map(converter::convertToDTO)
                .toList();
    }

    // İstediğin kullanıcının bilgilerini güncelle.
    public void updateUser(int userId, UserDto userDto) {
        User user = findUser.findUser(userId);

        updateField(userDto.getKullaniciTakmaAd(), user::setKullaniciTakmaAd);
        updateField(userDto.getKullaniciBio(), user::setKullaniciBio);
        updateField(userDto.getKullaniciTelefonNo(), user::setKullaniciTelefonNo);

        userRepository.save(user);
    }

    private void updateField(String newValue, Consumer<String> setter) {
        if (newValue != null && !newValue.isEmpty()) {
            setter.accept(newValue);
        }
    }
}
