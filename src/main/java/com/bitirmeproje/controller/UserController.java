package com.bitirmeproje.controller;

import com.bitirmeproje.dto.user.ChangeEmailDto;
import com.bitirmeproje.dto.user.SifreDegistirDto;
import com.bitirmeproje.dto.user.UserDto;
import com.bitirmeproje.dto.user.UserUpdateDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.email.SendEmail;
import com.bitirmeproje.helper.email.otp.OtpGenerator;
import com.bitirmeproje.helper.email.otp.OtpStorage;
import com.bitirmeproje.model.User;
import com.bitirmeproje.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080") // Doğru kullanım
public class UserController {
    private final IUserService userService;
    private final SendEmail emailService;
    private final OtpStorage otpStorage;

    public UserController(IUserService userService,
                          SendEmail emailService, OtpStorage otpStorage) {
        this.userService = userService;
        this.emailService = emailService;
        this.otpStorage = otpStorage;
    }

    // Kullanıcı şifresini değiştirme API'si(eski şifresini biliyor)
    @PostMapping("/sifre-degistir")
    public ResponseEntity<String> kullaniciSifreDegistir(
            @RequestBody SifreDegistirDto sifreDto) {

        userService.passwordChange(sifreDto);
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
    @GetMapping("/arama")
    public ResponseEntity <List<UserDto>> searchUsers(@RequestParam String query) {

        List<UserDto> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    // Kullanıcı bilgilerini yenile
    @PutMapping("/kullanici-bilgi-yenile")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDto userUpdateDto){

        userService.updateUser(userUpdateDto);
        return ResponseEntity.ok("Kullanıcı bilgileri başarıyla güncellendi.");
    }

    // Kullanıcının profilini getir
    @GetMapping("/me")
    public ResponseEntity<UserDto> findProfil() {

        UserDto currentUser = userService.findUserById();
        return ResponseEntity.ok(currentUser);
    }

    // Kullanıcı e-posta değiştiriyoruz
    @PostMapping("/eposta-degistir")
    public ResponseEntity<String> changeEmail(@RequestBody ChangeEmailDto changeEmailDto) {

        userService.changeUserEmail(changeEmailDto);
        return ResponseEntity.ok("E-posta başarıyla güncellendi.");
    }
}
