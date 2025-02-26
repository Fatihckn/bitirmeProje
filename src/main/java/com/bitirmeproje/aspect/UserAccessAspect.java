package com.bitirmeproje.aspect;

import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.model.User;
import com.bitirmeproje.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Parameter;
import java.util.Optional;

@Aspect
@Component
public class UserAccessAspect {

    private final UserService userService;

    public UserAccessAspect(UserService userService) {
        this.userService = userService;
    }

    @Before("@annotation(com.bitirmeproje.helper.RequireUserAccess)")
    public void validateUserAccess(JoinPoint joinPoint) {
        // SecurityContext'ten giriş yapan kullanıcının email'ini al
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> optionalCurrentUser = userService.findByEposta(email);
        if (optionalCurrentUser.isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Giriş yapan kullanıcı bulunamadı!");
        }

        User currentUser = optionalCurrentUser.get();

        // Metot parametrelerini al
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();

        String identifier = null;

        // Tüm @PathVariable parametrelerini kontrol et
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(PathVariable.class)) {
                identifier = args[i].toString();
                break; // İlk bulduğu PathVariable'ı alır
            }
        }

        if (identifier == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "ID veya Kullanıcı Adı parametresi bulunamadı!");
        }

        // Kullanıcı erişim yetkisini kontrol et (Hem ID hem Kullanıcı Adını destekler)
        if (!currentUser.getKullaniciTakmaAd().equals(identifier) &&
                !(currentUser.getKullaniciId() + "").equals(identifier)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "Başka bir kullanıcının hesabından erişilemez!");
        }
    }
}
