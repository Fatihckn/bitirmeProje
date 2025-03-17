package com.bitirmeproje.helper.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {
    private final JavaMailSender mailSender;

    public SendEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Şifre Sıfırlama Kodu");
            helper.setText("Şifre sıfırlamak için doğrulama kodunuz: " + otp);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("E-posta gönderilirken hata oluştu!");
        }
    }
}
