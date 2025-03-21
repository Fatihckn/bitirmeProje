package com.bitirmeproje.helper.email.sendemail;

import com.bitirmeproje.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendEmailForPasswordChange implements SendEmail {
    private final JavaMailSender mailSender;

    public SendEmailForPasswordChange(JavaMailSender mailSender) {
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
            throw new CustomException(HttpStatus.NOT_FOUND,"E-posta gönderilirken hata oluştu!");
        }
    }
}
