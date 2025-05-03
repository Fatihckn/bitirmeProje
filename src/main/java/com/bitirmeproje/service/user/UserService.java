package com.bitirmeproje.service.user;

import com.bitirmeproje.dto.gonderiler.GonderiDto;
import com.bitirmeproje.dto.user.*;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.dto.IEntityDtoConverter;
import com.bitirmeproje.helper.email.otp.OtpGenerator;
import com.bitirmeproje.helper.email.otp.OtpStorage;
import com.bitirmeproje.helper.email.sendemail.SendEmail;
import com.bitirmeproje.helper.email.sendemail.SendEmailForDeleteAccount;
import com.bitirmeproje.helper.email.sendemail.SendEmailForPasswordChange;
import com.bitirmeproje.helper.password.PasswordHasher;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.helper.user.GetUserByToken;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.FollowsRepository;
import com.bitirmeproje.repository.GonderilerRepository;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.service.r2storage.R2StorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final GetUserByToken getUserByToken;
    private final IEntityDtoConverter<User, UserDto> entityDtoConvert;
    private final FindUser<String> findUser;
    private final SendEmail emailServiceDeleteAccount;
    private final SendEmail emailService;
    private final OtpStorage otpStorage;
    private final R2StorageService r2StorageService;
    private final GonderilerRepository gonderilerRepository;
    private final FollowsRepository followsRepository;

    private final Map<String, String> sifremiUnuttumOtp = new ConcurrentHashMap<>();

    private final static String r2PublicBaseUrl = "https://media.bitirmeproje.xyz";

    UserService(UserRepository userRepository, PasswordHasher passwordHasher,
                GetUserByToken getUserByToken,
                @Qualifier("userConverter") IEntityDtoConverter<User, UserDto> entityDtoConvert,
                @Qualifier("sendEmailForPasswordChange") SendEmailForPasswordChange emailService,
                FindUser<String> findUser, OtpStorage otpStorage,
                R2StorageService r2StorageService, GonderilerRepository gonderilerRepository,
                FollowsRepository followsRepository, @Qualifier("sendEmailForDeleteAccount") SendEmailForDeleteAccount emailServiceDeleteAccount) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.getUserByToken = getUserByToken;
        this.entityDtoConvert = entityDtoConvert;
        this.findUser = findUser;
        this.emailService = emailService;
        this.otpStorage = otpStorage;
        this.r2StorageService = r2StorageService;
        this.gonderilerRepository = gonderilerRepository;
        this.followsRepository = followsRepository;
        this.emailServiceDeleteAccount = emailServiceDeleteAccount;
    }

    // Kullanıcnın şifresini değiştiriyoruz(Şifremi unuttum değil)
    public void passwordChange(SifreDegistirDto sifreDto) {
        User user = getUserByToken.getUser();

        // Eski şifreyi doğrula
        if (sifreDto == null || sifreDto.getEskiSifre() == null || sifreDto.getYeniSifre() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Şifre alanları boş olamaz!");
        }

        if (!passwordHasher.matches(sifreDto.getEskiSifre(), user.getSifre())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Eski şifre hatalı!");
        }

        // Yeni şifre eski şifre ile aynı olamaz
        if (sifreDto.getEskiSifre().equals(sifreDto.getYeniSifre())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Yeni şifre eski şifre ile aynı olamaz!");
        }

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

        String oncekiUrl = user.getKullaniciProfilResmi();
        if (oncekiUrl != null && oncekiUrl.contains("profile-pics/")) {
            // sadece R2'deki dosya yolunu al
            String eskiPath = oncekiUrl.substring(oncekiUrl.indexOf("profile-pics/"));
            r2StorageService.deleteFile(eskiPath);
        }

        // Benzersiz dosya adı
        String fileName = "profile-pics/user_" + user.getKullaniciId() + "_" + UUID.randomUUID() + ".jpg";

        // R2’ye dosyayı yükle
        String imageUrl = r2StorageService.uploadFile(profilResmi, fileName);

        // DB’ye public URL kaydet
        user.setKullaniciProfilResmi(r2PublicBaseUrl + "/" + imageUrl);
        userRepository.save(user);

    }

    // Sifre sifirlamak icin maile kod gonderilir
    public void sifreSifirla(String email) {
        // 1. Kullanıcı var mı yok mu kontrol et
        findUser.findUser(email);

        // 2. OTP üret
        String otp = OtpGenerator.generateOtp();

        // 3. OTP'yi sakla (belirli bir süre için)
        otpStorage.putOtp(email, otp);

        // 4. Mail at
        emailService.sendOtpEmail(email, otp);
    }

    // Maile gelen kodu dogrular
    public void sifreDogrula(String email, String otp) {
        boolean isValid = otpStorage.validateOtp(email, otp);

        if (!isValid) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,"Geçersiz, süresi dolmuş OTP veya e-posta!");
        }

        String sifreKod = OtpGenerator.generateOtp();

        putOtpWithExpiry(email, sifreKod); // 1 dakika süresi var
    }

    // Yeni sifre belirler
    public void yeniSifreBelirle(String email, String yeniSifre) {
        User user = findUser.findUser(email);

        if(sifremiUnuttumOtp.containsKey(email)) {
            passwordSave(user, yeniSifre);
            sifremiUnuttumOtp.remove(email);
        }
        else{
            throw new CustomException(HttpStatus.BAD_REQUEST,"Islem gerceklestirilemedi, lütfen tekrardan deneyiniz.");
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

        try{
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
            if (userDto.getKullaniciDogumTarihi() != null) {
                user.setKullaniciDogumTarihi(userDto.getKullaniciDogumTarihi());
            }
            if (userDto.getKullaniciCinsiyet() != null && !userDto.getKullaniciCinsiyet().isEmpty()) {
                user.setKullaniciCinsiyet(userDto.getKullaniciCinsiyet());
            }
            if (userDto.getKullaniciUyeUlkesi() != null && !userDto.getKullaniciUyeUlkesi().isEmpty()) {
                user.setKullaniciUyeUlkesi(userDto.getKullaniciUyeUlkesi());
            }

            userRepository.save(user);
        }catch (DataIntegrityViolationException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Bu bilgi daha önce alınmış, lütfen farklı bir bilgi giriniz.");
        }
        catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Beklenmeyen bir hata oluştu.");
        }

    }

    // ID'ye göre kullanıcı bilgilerini getir.
    public UserDto findUserById() {
        User users = getUserByToken.getUser();

        return entityDtoConvert.convertToDTO(users);
    }

    public UserGonderilerDto findUserByIdAranan(String takmaAd) {
        boolean flag = false;

        User users = userRepository.getUserByKullaniciTakmaAd(takmaAd);

        if(users == null) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kullanici Bulunamadi");
        }

        List<GonderiDto> gonderiler = gonderilerRepository.findProfilGonderileriWithBegeniDurumu(users.getKullaniciId(), getUserByToken.getUser().getKullaniciId());

        List<User> kullaniciTakipcisi = followsRepository.findByFollowersUserId(users.getKullaniciId());

        List<User> kullaniciTakipEttigi = followsRepository.findByFollowingUserId(users.getKullaniciId());

        for(User user : kullaniciTakipcisi) {
            if(user.getKullaniciId() == getUserByToken.getUser().getKullaniciId()) {
                flag = true;
            }
        }

        UserGonderilerDto userDto = new UserGonderilerDto(gonderiler, kullaniciTakipcisi.size(), kullaniciTakipEttigi.size(), flag);
        userDto.setKullaniciTakmaAd(users.getKullaniciTakmaAd());
        userDto.setKullaniciBio(users.getKullaniciBio());
        userDto.setKullaniciId(users.getKullaniciId());
        userDto.setKullaniciProfilResmi(users.getKullaniciProfilResmi());
        userDto.setKullaniciTelefonNo(users.getKullaniciTelefonNo());
        userDto.setKullaniciUyeOlmaTarihi(users.getKullaniciUyeOlmaTarihi());
        userDto.setePosta(users.getePosta());
        userDto.setKullaniciDogumTarihi(users.getKullaniciDogumTarihi());
        userDto.setKullaniciCinsiyet(users.getKullaniciCinsiyet());
        userDto.setKullaniciUyeUlkesi(users.getKullaniciUyeUlkesi());
        return userDto;
    }

    // Şifreyi kaydet
    public void passwordSave(User user, String yeniSifre) {
        user.setSifre(passwordHasher.hashPassword(yeniSifre));
        userRepository.save(user);
    }

    // Kullanıcı e-posta değiştiriyoruz
    // form verisini ilk olarak buraya gondereceğim.
    public void changeUserEmail(ChangeEmailDto changeEmailDto) {
        User user = getUserByToken.getUser();

        String otp = OtpGenerator.generateOtp();

        otpStorage.putOtp(changeEmailDto.getYeniEposta(), otp);

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

        emailServiceDeleteAccount.sendOtpEmail(changeEmailDto.getYeniEposta(), otp);
    }

    @Transactional
    public void deleteUserAccount(String otp) {
        User user = getUserByToken.getUser();

        otpStorage.validateOtp(user.getePosta(), otp);

        // Kullanıcıyı ve tüm ilişkili verileri sil
        userRepository.delete(user);
    }

    // Genel validasyon(sadece login olan kullanıcının emailine)
    public void validation() {
        User user = getUserByToken.getUser();

        String otp = OtpGenerator.generateOtp();

        otpStorage.putOtp(user.getePosta(), otp);

        emailServiceDeleteAccount.sendOtpEmail(user.getePosta(), otp);
    }

    // E-mail değiştirmek için validasyon
    public void validationForEmail(ChangeEmailDto2 changeEmailDto2){
        User user = getUserByToken.getUser();

        otpStorage.validateOtp(changeEmailDto2.getYeniEposta(), changeEmailDto2.getOtp());

        user.setePosta(changeEmailDto2.getYeniEposta());
        userRepository.save(user);
    }

    private void putOtpWithExpiry(String email, String otp) {
        sifremiUnuttumOtp.put(email, otp);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sifremiUnuttumOtp.remove(email);
            }
        }, 60 * 1000);
    }
}
