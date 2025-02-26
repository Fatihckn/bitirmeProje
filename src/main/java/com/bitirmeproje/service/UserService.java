package com.bitirmeproje.service;

import com.bitirmeproje.dto.*;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.PasswordHasher;
import com.bitirmeproje.model.Follows;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.FollowsRepository;
import com.bitirmeproje.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final FollowsRepository followsRepository;

    UserService(UserRepository userRepository, PasswordHasher passwordHasher, FollowsRepository followsRepository) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.followsRepository = followsRepository;
    }

    // Kullanıcnın şifresini değiştiriyoruz(Şifremi unuttum değil)
    public void passwordChange(User user, SifreDegistirDto sifreDto) {
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

//    public void profilResmiGuncelle(User user, ProfilResmiGuncelleDto profilResmiGuncelle){
//        MultipartFile profilResmi = profilResmiGuncelle.getProfilResmi();
//
//        if (profilResmi == null || profilResmi.isEmpty()) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, "Profil resmi boş olamaz");
//        }
//
//        try {
//            user.setKullaniciProfilResmi(profilResmi.getBytes());
//            userRepository.save(user);
//        } catch (IOException e){
//            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Profil resmi kaydedilirken hata oluştu")
//        }
//
//    }

    // Aradığın kullanıcının bilgileri getiriliyor(Rolü, id'si gibi bilgiler hariç)
    public List<UserDto> searchUsers(String query) {
        List<User> users = userRepository.searchByQuery(query);

        if (users.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanıcı bulunamadı");
        }

        return users.stream()
                .map(user -> new UserDto(
                        user.getKullaniciTakmaAd(),
                        user.getePosta(),
                        user.getKullaniciBio(),
                        user.getKullaniciProfilResmi(),
                        user.getKullaniciTelefonNo(),
                        user.getKullaniciDogumTarihi(),
                        user.getKullaniciUyeOlmaTarihi()
                ))
                .toList();
    }

    // Kullanıcının istediği kişiyi takip et
    public void followUser(User follower, int followingId) {
        Optional<User> followingUserOptional = userRepository.findByKullaniciId(followingId);

        if (followingUserOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Takip edilmek istenen kullanıcı bulunamadı!");
        }

        User followingUser = followingUserOptional.get();

        // Kullanıcı kendi kendisini takip edemez
        if (follower.getKullaniciId() == followingUser.getKullaniciId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Kendi kendinizi takip edemezsiniz!");
        }

        // Kullanıcı zaten takip ediyorsa hata döndür
        if (followsRepository.findByFollowerAndFollowing(follower, followingUser).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu kullanıcıyı zaten takip ediyorsunuz!");
        }

        // Yeni takip kaydı oluştur
        Follows follows = new Follows();
        follows.setTakipEdenKullaniciId(follower);
        follows.setTakipEdilenKullaniciId(followingUser);
        follows.setTakipEtmeTarihi(LocalDate.now());

        followsRepository.save(follows);
    }

    // Kullanıcının istediği kişiyi takipten çık
    public void unfollowUser(User follower, int takipEdilenId) {
        Optional<User> followingUserOptional = userRepository.findByKullaniciId(takipEdilenId);

        if (followingUserOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Takipten çıkılmak istenen kullanıcı bulunamadı!");
        }

        User followingUser = followingUserOptional.get();

        // Kullanıcı kendi kendisini takipten çıkamaz çünkü zaten takip etmiyor
        if (follower.getKullaniciId() == followingUser.getKullaniciId()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Kendi kendinizi takipten çıkaramazsınız!");
        }

        // Kullanıcının gerçekten takip edip etmediğini kontrol et
        Optional<Follows> followRecordOptional = followsRepository.findByFollowerAndFollowing(follower, followingUser);

        if (followRecordOptional.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu kullanıcıyı takip etmiyorsunuz!");
        }

        // Takip kaydını veritabanından sil
        followsRepository.delete(followRecordOptional.get());
    }

    // Kullanıcıyı takip eden kişileri getir
    public List<UserDto> getFollowers(int userId) {
        Optional<User> userOptional = userRepository.findByKullaniciId(userId);

        if (userOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!");
        }

        List<User> followers = followsRepository.findByFollowersUserId(userId);

        if (followers.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Bu kullanıcının takipçisi bulunmamaktadır.");
        }

        return followers.stream()
                .map(user -> new UserDto(
                        user.getKullaniciTakmaAd(),
                        user.getePosta(),
                        user.getKullaniciBio(),
                        user.getKullaniciProfilResmi(),
                        user.getKullaniciTelefonNo(),
                        user.getKullaniciDogumTarihi(),
                        user.getKullaniciUyeOlmaTarihi()
                ))
                .toList();
    }

    // Kullanıcının takip ettiği kişileri getir.
    public List<UserDto> getFollowing(int userId) {
        Optional<User> userOptional = userRepository.findByKullaniciId(userId);

        if (userOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!");
        }

        List<User> following = followsRepository.findByFollowingUserId(userId);

        if (following.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Bu kullanıcı kimseyi takip etmiyor.");
        }

        return following.stream()
                .map(user -> new UserDto(
                        user.getKullaniciTakmaAd(),
                        user.getePosta(),
                        user.getKullaniciBio(),
                        user.getKullaniciProfilResmi(),
                        user.getKullaniciTelefonNo(),
                        user.getKullaniciDogumTarihi(),
                        user.getKullaniciUyeOlmaTarihi()
                ))
                .toList();
    }

    // Kullanıcının bilgilerini güncelle.
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

    // ID'ye göre kullanıcı bilgilerini getir.
    public List<UserDto> findUserById(int id) {
        Optional<User> users = userRepository.findById(id);

        if (users.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanıcı bulunamadı");
        }

        return users.stream()
                .map(user -> new UserDto(
                        user.getKullaniciTakmaAd(),
                        user.getePosta(),
                        user.getKullaniciBio(),
                        user.getKullaniciProfilResmi(),
                        user.getKullaniciTelefonNo(),
                        user.getKullaniciDogumTarihi(),
                        user.getKullaniciUyeOlmaTarihi()
                ))
                .toList();
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
    public void changeUserEmail(int userId, ChangeEmailDto changeEmailDto) {
        Optional<User> userOptional = userRepository.findByKullaniciId(userId);

        if (userOptional.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı!");
        }

        User user = userOptional.get();

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
