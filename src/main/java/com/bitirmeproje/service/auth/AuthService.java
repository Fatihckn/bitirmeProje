package com.bitirmeproje.service.auth;

import com.bitirmeproje.dto.auth.LoginDto;
import com.bitirmeproje.dto.user.RegisterDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.email.sendemail.SendEmail;
import com.bitirmeproje.helper.email.otp.OtpGenerator;
import com.bitirmeproje.helper.email.otp.OtpStorage;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.Role;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.security.jwt.JwtUtil;
import com.bitirmeproje.service.blacklistservice.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final FindUser<String> findUser;
    private final OtpStorage otpStorage;
    private final SendEmail sendEmail;
    private final Map<String, RegisterDto> pendingUsers = new ConcurrentHashMap<>();
    private final HttpServletRequest request;
    private final TokenBlacklistService tokenBlacklistService;

    private final static String r2PublicBaseUrl = "https://media.bitirmeproje.xyz";

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                JwtUtil jwtUtil,@Qualifier("findUserByEmail") FindUser<String> findUser,
                OtpStorage otpStorage,SendEmail sendEmail,
                HttpServletRequest request, TokenBlacklistService tokenBlacklistService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.findUser = findUser;
        this.otpStorage = otpStorage;
        this.sendEmail = sendEmail;
        this.request = request;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    // Kullanıcı kayıt servisi
    public void registerUser(RegisterDto user) {
        if(userRepository.findByEPosta(user.getePosta()).isPresent() || userRepository.findByKullaniciTakmaAd(user.getKullaniciTakmaAd()).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Bu e-posta veya kullanıcı adı zaten kullanılıyor!");
        }

        String otp = OtpGenerator.generateOtp();

        otpStorage.putOtp(user.getePosta(), otp);

        sendEmail.sendOtpEmail(user.getePosta(), otp);

        pendingUsers.put(user.getePosta(), user);
    }

    // Maile gelen kod ile doğrulama başarılı olursa kullanıcı kayıt olsun.
    public void verifyOtpAndRegister(String email, String otp) {
        if (!otpStorage.validateOtp(email, otp)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Geçersiz veya süresi dolmuş OTP!");
        }

        RegisterDto user = pendingUsers.remove(email); // Kullanıcıyı geçici listeden çıkar

        if (user == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Kayıt bilgileri bulunamadı!");
        }

//        Bu Kodu kayıt olurken admin rolü ekleyecekseniz açınız, hep açık olursa herkes admin olarak kayıt olabilir!!!
//        (Bunu açarsanız alttakini geçici olarak yorum satırına alın)
//        if(user.getKullaniciRole() == null) {
//            user.setKullaniciRole(Role.USER);
//        }

        User registerUser = new User();

        registerUser.setKullaniciCinsiyet(user.getKullaniciCinsiyet());
        registerUser.setePosta(user.getePosta());
        registerUser.setKullaniciDogumTarihi(user.getKullaniciDogumTarihi());
        registerUser.setKullaniciRole(Role.USER);
        registerUser.setKullaniciTakmaAd(user.getKullaniciTakmaAd());
        registerUser.setKullaniciCinsiyet(user.getKullaniciCinsiyet());
        registerUser.setKullaniciTelefonNo(user.getKullaniciTelefonNo());
        registerUser.setKullaniciUyeUlkesi(user.getKullaniciUyeUlkesi());

        // Varsayılan profil resmi ata
        registerUser.setKullaniciProfilResmi(r2PublicBaseUrl + "/profile-pics/empty.png");

        // Şifreyi hashle
        registerUser.setSifre(passwordEncoder.encode(user.getSifre()));

        // Kullanıcıyı kaydet
        userRepository.save(registerUser);
    }

    public String login(LoginDto loginDto) {
        User user = findUser.findUser(loginDto.getePosta());

        if (!passwordEncoder.matches(loginDto.getSifre(), user.getSifre())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,"Hatalı şifre!");
        }

        // Başarılı giriş, token üret ve geri döndür
        return jwtUtil.generateToken(user.getePosta(), user.getKullaniciRole().name(), user.getKullaniciId(), user.getKullaniciTakmaAd());
    }

    public void logout() {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Token Bulunamadi");
        }

        String token = authHeader.substring(7);

        Date expiration = jwtUtil.extractExpiration(token);
        long now = System.currentTimeMillis();
        long expMillis = expiration.getTime() - now;

        // Redis'e token'ı blacklist'e at
        tokenBlacklistService.blacklistToken(token, expMillis);
    }
}
