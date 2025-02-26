package com.bitirmeproje.helper;

import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.User;
import com.bitirmeproje.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAccessValidator {

    private final UserService userService;

    public UserAccessValidator(UserService userService) {
        this.userService = userService;
    }

    /**
     * Kullanıcının erişim yetkisini doğrular.
     *
     * @param identifier Kullanıcı ID veya Kullanıcı Takma Adı
     * @return Eğer yetkilendirme başarılıysa, ilgili kullanıcıyı döndürür.
     */
    public User validateUserAccess(Object identifier) {
        // SecurityContext'ten giriş yapan kullanıcının email'ini al
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Email'e göre giriş yapan kullanıcıyı bul
        Optional<User> optionalCurrentUser = userService.findByEposta(email);
        if (optionalCurrentUser.isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Giriş yapan kullanıcı bulunamadı!");
        }

        User currentUser = optionalCurrentUser.get();

        // Kullanıcının erişim yetkisini kontrol et
        if (identifier instanceof Integer) {  // Eğer gelen değer int (id) ise
            int userId = (Integer) identifier;
            if (currentUser.getKullaniciId() != userId) {
                throw new CustomException(HttpStatus.FORBIDDEN, "Başka bir kullanıcının hesabına erişilemez!");
            }
        } else if (identifier instanceof String) {  // Eğer gelen değer String (kullanıcı adı) ise
            String username = (String) identifier;
            if (!currentUser.getKullaniciTakmaAd().equals(username)) {
                throw new CustomException(HttpStatus.FORBIDDEN, "Başka bir kullanıcının hesabına erişilemez!");
            }
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Geçersiz identifier türü!");
        }

        return currentUser;
    }

}
