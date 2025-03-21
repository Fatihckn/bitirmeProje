package com.bitirmeproje.service.auth;

import com.bitirmeproje.dto.auth.LoginDto;
import com.bitirmeproje.exception.CustomException;
import com.bitirmeproje.helper.email.sendemail.SendEmail;
import com.bitirmeproje.helper.email.otp.OtpGenerator;
import com.bitirmeproje.helper.email.otp.OtpStorage;
import com.bitirmeproje.helper.user.FindUser;
import com.bitirmeproje.model.Role;
import com.bitirmeproje.model.User;
import com.bitirmeproje.repository.UserRepository;
import com.bitirmeproje.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final Map<String, User> pendingUsers = new ConcurrentHashMap<>();

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                JwtUtil jwtUtil, FindUser<String> findUser,
                OtpStorage otpStorage,@Qualifier("sendEmailForRegister") SendEmail sendEmail) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.findUser = findUser;
        this.otpStorage = otpStorage;
        this.sendEmail = sendEmail;
    }

    // Kullanıcı kayıt servisi
    public void registerUser(User user) {
        String otp = OtpGenerator.generateOtp();

        otpStorage.putOtp(user.getePosta(), otp);

        sendEmail.sendOtpEmail(user.getePosta(), otp);

        pendingUsers.put(user.getePosta(), user);
    }

    public void verifyOtpAndRegister(String email, String otp) {
        if (!otpStorage.validateOtp(email, otp)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Geçersiz veya süresi dolmuş OTP!");
        }

        User user = pendingUsers.remove(email); // Kullanıcıyı geçici listeden çıkar

        if (user == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Kayıt bilgileri bulunamadı!");
        }

        if(user.getKullaniciRole() == null) {
            user.setKullaniciRole(Role.USER);
        }

        if(userRepository.findByEPosta(user.getePosta()).isPresent() || userRepository.findByKullaniciTakmaAd(user.getKullaniciTakmaAd()).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Bu e-posta veya kullanıcı adı zaten kullanılıyor!");
        }

        // Varsayılan profil resmi ata
        user.setKullaniciProfilResmi("/uploads/profile-pics/empty.png");

        // Şifreyi hashle
        user.setSifre(passwordEncoder.encode(user.getSifre()));

        // Kullanıcıyı kaydet
        userRepository.save(user);
    }

    public String login(LoginDto loginDto) {
        User user = findUser.findUser(loginDto.getePosta());

        if (!passwordEncoder.matches(loginDto.getSifre(), user.getSifre())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED,"Hatalı şifre!");
        }

        // Başarılı giriş, token üret ve geri döndür
        return jwtUtil.generateToken(user.getePosta(), user.getKullaniciRole().name(), user.getKullaniciId());
    }
}
