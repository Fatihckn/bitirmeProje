package com.bitirmeproje.service.user;

import com.bitirmeproje.dto.user.*;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.password.PasswordHasher;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final GetUserByToken getUserByToken;
    private final IEntityDtoConverter<User, UserDto> entityDtoConvert;

    @Value("${upload.folder}") // application.properties’ten okunacak
    private String uploadFolder;

    UserService(UserRepository userRepository, PasswordHasher passwordHasher,
                GetUserByToken getUserByToken,@Qualifier("userConverterer") IEntityDtoConverter<User, UserDto> entityDtoConvert) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.getUserByToken = getUserByToken;
        this.entityDtoConvert = entityDtoConvert;
    }

    // Kullanıcnın şifresini değiştiriyoruz(Şifremi unuttum değil)
    public void passwordChange(SifreDegistirDto sifreDto) {
        User user = getUserByToken.getUser();

        // Eski şifreyi doğrula
//        System.out.println("satır 49 geldi");
        if (sifreDto == null || sifreDto.getEskiSifre() == null || sifreDto.getYeniSifre() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Şifre alanları boş olamaz!");
        }
//        System.out.println("satır 53 geldi, eski şifre ile yeni şifre aynı değil");

        if (!passwordHasher.matches(sifreDto.getEskiSifre(), user.getSifre())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Eski şifre hatalı!");
        }

        // Yeni şifre eski şifre ile aynı olamaz
        if (sifreDto.getEskiSifre().equals(sifreDto.getYeniSifre())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Yeni şifre eski şifre ile aynı olamaz!");
        }
//        System.out.println("satır 53 geldi, eski şifre ile yeni şifre aynı değil");

        // Yeni şifreyi hashle ve kaydet
        user.setSifre(passwordHasher.hashPassword(sifreDto.getYeniSifre()));
        userRepository.save(user);
    }

    public void profilResmiGuncelle(ProfilResmiGuncelleDto profilResmiGuncelle) {
        User user = getUserByToken.getUser();

        MultipartFile profilResmi = profilResmiGuncelle.getProfilResmi();

        if (profilResmi == null || profilResmi.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Profil resmi boş olamaz");
        }

        try {
            // Klasörü oluştur (eğer yoksa)
            File uploadDir = new File(uploadFolder + "/profile-pics/");
            if (!uploadDir.exists()) {
                boolean isCreated = uploadDir.mkdirs();
                if (!isCreated) {
                    throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Profil resmi klasörü oluşturulamadı!");
                }
            }

            // Dosya adını belirle (örn: user_12.jpg)
            String fileName = "user_" + user.getKullaniciId() + ".jpg";
            Path filePath = Paths.get(uploadFolder + "/profile-pics/" + fileName);

            // Dosyayı kaydet
            Files.write(filePath, profilResmi.getBytes());

            // DB'ye yolu kaydet
            user.setKullaniciProfilResmi("/uploads/profile-pics/" + fileName);
            userRepository.save(user);

        } catch (IOException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Profil resmi kaydedilirken hata oluştu");
        }
    }

    // Aradığın kullanıcının bilgileri getiriliyor(Rolü, id'si gibi bilgiler hariç)
    public List<UserDto> searchUsers(String query) {
        List<User> users = userRepository.searchByQuery(query);

        if (users.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanıcı bulunamadı");
        }

        return users.stream()
                .map(entityDtoConvert::convertToDTO)
                .toList();
    }

    // Kullanıcının bilgilerini güncelle.
    public void updateUser(UserDto userDto) {

        User user = getUserByToken.getUser();

        // Güncellenen değerleri boş değilse ata, boşsa eski değerleri koru
        if (userDto.getKullaniciTakmaAd() != null && !userDto.getKullaniciTakmaAd().isEmpty()) {
            user.setKullaniciTakmaAd(userDto.getKullaniciTakmaAd());
        }
        if (userDto.getKullaniciBio() != null && !userDto.getKullaniciBio().isEmpty()) {
            user.setKullaniciBio(userDto.getKullaniciBio());
        }
        if (userDto.getKullaniciTelefonNo() != null && !userDto.getKullaniciTelefonNo().isEmpty()) {
            user.setKullaniciTelefonNo(userDto.getKullaniciTelefonNo());
        }

        // Güncellenmiş kullanıcıyı kaydet
        userRepository.save(user);
    }

    // ID'ye göre kullanıcı bilgilerini getir.
    public UserDto findUserById() {
        User users = getUserByToken.getUser();

        return entityDtoConvert.convertToDTO(users);
    }

    // Şifreyi kaydet
    public void passwordSave(User user, String yeniSifre) {
        user.setSifre(passwordHasher.hashPassword(yeniSifre));
        userRepository.save(user);
    }

    // E-posta'ya göre kullanıcı bilgilerini getir.
    public Optional<User> findByEposta (String ePosta) {
        return userRepository.findByEPosta(ePosta);
    }

    // Kullanıcı e-posta değiştiriyoruz
    public void changeUserEmail(ChangeEmailDto changeEmailDto) {
        User user = getUserByToken.getUser();

        // Eski e-posta eşleşiyor mu kontrol et
        if (!user.getePosta().equals(changeEmailDto.getEskiEposta())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Eski e-posta yanlış!");
        }

        // Şifre doğrulaması yap
        if (!passwordHasher.matches(changeEmailDto.getSifre(), user.getSifre())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Şifre yanlış!");
        }

        // Yeni e-posta daha önce kullanılmış mı kontrol et
        if (userRepository.findByEPosta(changeEmailDto.getYeniEposta()).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu e-posta adresi zaten kullanımda!");
        }

        // Yeni e-postayı kaydet
        user.setePosta(changeEmailDto.getYeniEposta());
        userRepository.save(user);
    }
}
