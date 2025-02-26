package com.bitirmeproje.helper;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class OtpStorage {
    private final ConcurrentHashMap<String, String> otpMap = new ConcurrentHashMap<>();

    // OTP'yi ekle
    public void putOtp(String email, String otp) {
        otpMap.put(email, otp);
    }

    // OTP'yi al
    public String getOtp(String email) {
        return otpMap.get(email);
    }

    // OTP'yi sil (zaman aşımı sonrası)
    public void removeOtp(String email) {
        otpMap.remove(email);
    }

    // OTP doğrulama
    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpMap.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            removeOtp(email); // Kullanıldıktan sonra OTP'yi sil
            return true;
        }
        return false;
    }
}
