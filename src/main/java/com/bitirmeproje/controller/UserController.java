package com.bitirmeproje.controller;

import com.bitirmeproje.dto.user.*;
import com.bitirmeproje.service.user.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // Kullanıcı şifresini değiştirme API'si(eski şifresini biliyor)
    @PostMapping("/sifre-degistir")
    public ResponseEntity<String> kullaniciSifreDegistir(
            @RequestBody SifreDegistirDto sifreDto) {

        userService.passwordChange(sifreDto);
        return ResponseEntity.ok("Şifre başarıyla değiştirildi, lütfen tekrar giriş yapınız!");
    }

    @PutMapping("/profil-resmi-degistir")
    public ResponseEntity<String> kullaniciProfilResmiGuncelle(@RequestParam("resim") MultipartFile resim) {

        userService.profilResmiGuncelle(convertToDto(resim));

        return ResponseEntity.ok("Profil resmi başarıyla güncellendi.");
    }

    // Kullanıcı şifremi unuttum API'si
    // Kullanıcı şifre sıfırlama ekranında mailini girecek
    @PostMapping("/sifre-sifirla")
    public ResponseEntity<String> sifreSifirla(@RequestParam String email) {
        userService.sifreSifirla(email);

        return ResponseEntity.ok("Şifre sıfırlama kodu e-posta adresinize gönderildi.");
    }

    // Kullanıcı şifremi unuttum API'si
    // Kullanıcı mail'ine gelen doğrulama kodunu girecek
    @PostMapping("/sifre-dogrula")
    public ResponseEntity<String> sifreDogrula(@RequestParam String email, @RequestParam String otp) {
        userService.sifreDogrula(email, otp);

        return ResponseEntity.ok("OTP doğrulandı, yeni şifre belirleyebilirsiniz.");
    }

    // Kullanıcı şifremi unuttum API'si
    // Girdiği doğrulama kodu doğru ise yeni şifre belirleyecek
    @PostMapping("/yeni-sifre-belirle")
    public ResponseEntity<String> yeniSifreBelirle(@RequestParam String email, @RequestParam String yeniSifre) {
        userService.yeniSifreBelirle(email, yeniSifre);
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
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto){

        userService.updateUser(userDto);
        return ResponseEntity.ok("Kullanıcı bilgileri başarıyla güncellendi.");
    }

    // Kullanıcının profilini getir
    @GetMapping("/me")
    public ResponseEntity<UserDto> findProfil() {

        UserDto currentUser = userService.findUserById();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/profil/{takmaAd}")
    public ResponseEntity<UserGonderilerDto> findProfilAranan(@PathVariable String takmaAd) {

        UserGonderilerDto currentUser = userService.findUserByIdAranan(takmaAd);
        return ResponseEntity.ok(currentUser);
    }

    // Kullanıcı e-posta değiştiriyoruz
    @PostMapping("/eposta-degistir")
    public ResponseEntity<String> changeEmail(@RequestBody ChangeEmailDto changeEmailDto) {

        userService.changeUserEmail(changeEmailDto);
        return ResponseEntity.ok("E-posta başarıyla güncellendi.");
    }

    // Hesap silme
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestParam String otp) {
        userService.deleteUserAccount(otp);
        return ResponseEntity.ok("Hesap Başarıyla Silindi");
    }

    // Genel validasyon(sadece login olan kullanıcının emailine)
    @PostMapping("/validation")
    public ResponseEntity<String> validateEmail() {
        userService.validation();
        return ResponseEntity.ok("Mailinize Kod Gönderildi.");
    }

    // E-mail değiştirmek için validasyon
    @PostMapping("/validation/email")
    public ResponseEntity<String> validateEmail(@RequestParam String email) {
        userService.validationForEmail(email);
        return ResponseEntity.ok("Mailinize Kod Gönderildi.");
    }

    private ProfilResmiGuncelleDto convertToDto(MultipartFile profilResmi) {
        ProfilResmiGuncelleDto profilResmiGuncelleDto = new ProfilResmiGuncelleDto();
        profilResmiGuncelleDto.setProfilResmi(profilResmi);
        return profilResmiGuncelleDto;
    }
}
