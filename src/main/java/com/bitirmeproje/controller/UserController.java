package com.bitirmeproje.controller;

import com.bitirmeproje.dto.*;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.OtpGenerator;
import com.bitirmeproje.helper.OtpStorage;
import com.bitirmeproje.helper.RequireUserAccess;
import com.bitirmeproje.helper.UserAccessValidator;
import com.bitirmeproje.model.User;
import com.bitirmeproje.service.EmailService;
import com.bitirmeproje.service.IUserService;
import com.bitirmeproje.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;
    private final UserAccessValidator userAccessValidator;
    private final EmailService emailService;
    private final OtpStorage otpStorage;

    public UserController(UserService userService, UserAccessValidator userAccessValidator,
                          EmailService emailService, OtpStorage otpStorage) {
        this.userService = userService;
        this.userAccessValidator = userAccessValidator;
        this.emailService = emailService;
        this.otpStorage = otpStorage;
    }

    // Kullanıcı şifresini değiştirme API'si(eski şifresini biliyor)
    @PostMapping("/{id}/sifre-degistir")
    public ResponseEntity<String> kullaniciSifreDegistir(
            @PathVariable int id,
            @RequestBody SifreDegistirDto sifreDto) {

        User currentUser = userAccessValidator.validateUserAccess(id);
        //System.out.println("kullanıcı doğrulandı");
        userService.passwordChange(currentUser, sifreDto);
        return ResponseEntity.ok("Şifre başarıyla değiştirildi, lütfen tekrar giriş yapınız!");
    }

//    // Kulanıcı profil resmi güncelleme API'si
//    @PutMapping("/kullanici/{id}/profil-resmi")
//    public ResponseEntity<String> kullaniciProfilResmiGuncelle(
//            @PathVariable String id,
//            @RequestParam("resim") ProfilResmiGuncelleDto profilResmiDto) {
//
//        User currentUser = userAccessValidator.validateUserAccess(id);
//        userService.profilResmiGuncelle(currentUser, new ProfilResmiGuncelleDto(profilResmi));
//
//        return ResponseEntity.ok("");
//    }

    // Kullanıcı şifremi unuttum API'si
    // Kullanıcı şifre sıfırlama ekranında mailini girecek
    @PostMapping("/sifre-sifirla")
    public ResponseEntity<String> sifreSifirla(@RequestParam String email) {
        // 1. Kullanıcı var mı yok mu kontrol et
        Optional<User> userOptional = userService.findByEposta(email);
        if (userOptional.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu e-posta adresine sahip kullanıcı bulunamadı");
        }

        // 2. OTP üret
        String otp = OtpGenerator.generateOtp();

        // 3. OTP'yi sakla (belirli bir süre için)
        otpStorage.putOtp(email, otp);

        // 4. Mail at
        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok("Şifre sıfırlama kodu e-posta adresinize gönderildi.");
    }

    // Kullanıcı şifremi unuttum API'si
    // Kullanıcı mail'ine gelen doğrulama kodunu girecek
    @PostMapping("/sifre-dogrula")
    public ResponseEntity<String> sifreDogrula(@RequestParam String email, @RequestParam String otp) {

        boolean isValid = otpStorage.validateOtp(email, otp);

        if (!isValid) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,"Geçersiz, süresi dolmuş OTP veya e-posta!");
        }

        return ResponseEntity.ok("OTP doğrulandı, yeni şifre belirleyebilirsiniz.");
    }

    //!!!!!! Zafiyet mevcut, kullanıcı kodu doğrulasa bile url de değişiklik yaparak buraya erişebilir
    // Bu zafiyeti, kullanıcı mailine gelen kod ile doğruladıktan sonra bir token oluşturup (passwordResetToken)
    // bu token'ı kullanıcı yeni şifre belirleme API'sine eriştiği zaman kontrol edip ona göre yeni şifre belirlemesini isteyebiliriz.
    // Kullanıcı şifremi unuttum API'si
    // Girdiği doğrulama kodu doğru ise yeni şifre belirleyecek
    @PostMapping("/yeni-sifre-belirle")
    public ResponseEntity<String> yeniSifreBelirle(@RequestParam String email, @RequestParam String yeniSifre) {
        // Kullanıcıyı bul
        Optional<User> userOptional = userService.findByEposta(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı!");
        }

        User user = userOptional.get();

        // Yeni şifreyi hash'leyerek güncelle
//        userService.  // Şifreyi kaydet
        userService.passwordSave(user, yeniSifre);

        return ResponseEntity.ok("Şifre başarıyla değiştirildi.");
    }

    // Aradığın kullanıcının bilgileri getiriliyor(Rolü, id'si gibi bilgiler hariç)
    @GetMapping("/{id}/arama")
    @RequireUserAccess  // Token ile istek atan kullanıcının doğru olup olmadığını kontrol ettikten sonra user tipinde o kullanıcıyı döndürmek isterseniz,
    // UserAccessValidator sınıfını kullanın. Örneğin;" userService.findByEposta(email); "
    // Burada id kullanılmıyor gibi görünebilir ancak RequireUserAccess anotasyonunda bu id ile kullanıcının kimlik doğrulaması yapılıyor.
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String query, @PathVariable int id) {

        List<UserDto> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    // Bir kullanıcıyı takip et
    @PostMapping("/{id}/takip-et")
    public ResponseEntity<String> followUser(@PathVariable int id, @RequestParam int takipEdilenId) {

        User currentUser = userAccessValidator.validateUserAccess(id);

        userService.followUser(currentUser, takipEdilenId);
        return ResponseEntity.ok("Kullanıcı başarıyla takip edildi.");
    }

    // Bir kullanıcıyı takipten çık
    @DeleteMapping("/{id}/takibi-bırakma")
    public ResponseEntity<String> unfollowUser(@PathVariable int id, @RequestParam int takipEdilenId) {

        User currentUser = userAccessValidator.validateUserAccess(id);

        userService.unfollowUser(currentUser, takipEdilenId);
        return ResponseEntity.ok("Kullanıcı başarıyla takipten çıkıldı");
    }

    // Kullanıcının takipçilerini getir
    @GetMapping("/{id}/takipciler")
    @RequireUserAccess
    public ResponseEntity<List<UserDto>> getFollowers(@PathVariable int id) {

        List<UserDto> followers = userService.getFollowers(id);
        return ResponseEntity.ok(followers);
    }

    // Kullanıcının takip ettiği kişileri getir
    @GetMapping("/{id}/takip-edilenler")
    @RequireUserAccess
    public ResponseEntity<List<UserDto>> getFollowing(@PathVariable int id) {

        List<UserDto> following = userService.getFollowing(id);
        return ResponseEntity.ok(following);
    }

    // Kullanıcı bilgilerini yenile
    @PutMapping("/{id}/kullanici-bilgi-yenile")
    @RequireUserAccess
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserUpdateDto userUpdateDto){

        userService.updateUser(id, userUpdateDto);
        return ResponseEntity.ok("Kullanıcı bilgileri başarıyla güncellendi.");
    }

    // Kullanıcının profilini getir
    @GetMapping("/{id}/me")
    @RequireUserAccess
    public ResponseEntity<List<UserDto>> findProfil(@PathVariable int id){

        List<UserDto> currentUser = userService.findUserById(id);
        return ResponseEntity.ok(currentUser);
    }

    // Kullanıcı e-posta değiştiriyoruz
    @PostMapping("/{id}/eposta-degistir")
    @RequireUserAccess
    public ResponseEntity<String> changeEmail(@PathVariable int id, @RequestBody ChangeEmailDto changeEmailDto) {

        userService.changeUserEmail(id, changeEmailDto);
        return ResponseEntity.ok("E-posta başarıyla güncellendi.");
    }


}
