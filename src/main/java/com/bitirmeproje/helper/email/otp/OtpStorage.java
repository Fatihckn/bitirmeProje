package com.bitirmeproje.helper.email.otp;

import com.bitirmeproje.dto.otp.OtpEntry;
import com.bitirmeproje.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OtpStorage {
    private final ConcurrentHashMap<String, OtpEntry> otpMap = new ConcurrentHashMap<>();
    private static final long OTP_VALIDITY_DURATION = 2 * 60 * 1000; // 2 dakika (milisaniye cinsinden)

    // OTP'yi ekle
    public void putOtp(String email, String otp) {
        long timestamp = System.currentTimeMillis(); // Şu anki zaman
        otpMap.put(email, new OtpEntry(otp, timestamp));
    }

    // OTP doğrulama (Zaman kontrolü ekledik)
    public boolean validateOtp(String email, String otp) {
        OtpEntry storedOtpEntry = otpMap.get(email);

        if (storedOtpEntry == null) {
            throw new CustomException(HttpStatus.NOT_FOUND,"Kod Bulunamadi!");
        }

        long currentTime = System.currentTimeMillis();
        long otpGeneratedTime = storedOtpEntry.timestamp();

        // 1 dakikalık süreyi kontrol et
        if ((currentTime - otpGeneratedTime) > OTP_VALIDITY_DURATION) {
            otpMap.remove(email); // Süresi doldu, OTP'yi kaldır
            throw new CustomException(HttpStatus.GATEWAY_TIMEOUT,"Kodun suresi dolmustur!");
        }

        // OTP eşleşiyor mu kontrol et
        if (storedOtpEntry.otp().equals(otp)) {
            otpMap.remove(email); // Doğrulandıktan sonra sil
            return true;
        }
        return false;
    }
}