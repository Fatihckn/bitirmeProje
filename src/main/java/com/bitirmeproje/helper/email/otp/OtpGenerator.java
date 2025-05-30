package com.bitirmeproje.helper.email.otp;

import java.security.SecureRandom;
import java.util.Random;

public class OtpGenerator {
    private static final int OTP_LENGTH = 6;

    public static String generateOtp() {
        Random random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));  // 0-9 arası rakam üretir
        }
        return otp.toString();
    }
}
