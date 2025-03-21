package com.bitirmeproje.helper.email.sendemail;

import com.bitirmeproje.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendEmailForRegister implements SendEmail {
    private final JavaMailSender mailSender;

    public SendEmailForRegister(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Kayıt olma kodu");
            helper.setText("Kayıt olmak için doğrulama kodunuz: " + otp);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new CustomException(HttpStatus.NOT_FOUND,"E-posta gönderilirken hata oluştu!");
        }
    }
}
