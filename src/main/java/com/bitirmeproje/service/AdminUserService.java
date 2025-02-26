package com.bitirmeproje.service;

import com.bitirmeproje.dto.UserAllDto;
import com.bitirmeproje.dto.UserUpdateDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminUserService implements IAdminUserService {

    private final UserRepository userRepository;

    AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        Optional<User> userOptional = userRepository.findByKullaniciId(userId);

        if (userOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!");
        }

        User user = userOptional.get();

        // Güncellenen değerleri boş değilse ata, boşsa eski değerleri koru
        if (userUpdateDto.getKullaniciTakmaAd() != null && !userUpdateDto.getKullaniciTakmaAd().isEmpty()) {
            user.setKullaniciTakmaAd(userUpdateDto.getKullaniciTakmaAd());
        }
        if (userUpdateDto.getKullaniciBio() != null && !userUpdateDto.getKullaniciBio().isEmpty()) {
            user.setKullaniciBio(userUpdateDto.getKullaniciBio());
        }
        if (userUpdateDto.getKullaniciTelefonNo() != null && !userUpdateDto.getKullaniciTelefonNo().isEmpty()) {
            user.setKullaniciTelefonNo(userUpdateDto.getKullaniciTelefonNo());
        }
        if (userUpdateDto.getKullaniciProfilResmi() != null && !userUpdateDto.getKullaniciProfilResmi().isEmpty()) {
            user.setKullaniciProfilResmi(userUpdateDto.getKullaniciProfilResmi());
        }

        // Güncellenmiş kullanıcıyı kaydet
        userRepository.save(user);
    }
}
