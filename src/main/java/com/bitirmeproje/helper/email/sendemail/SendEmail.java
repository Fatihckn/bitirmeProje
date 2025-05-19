package com.bitirmeproje.helper.email.sendemail;

import com.bitirmeproje.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
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
                helper.setSubject("Doğrulama Kodu");
                helper.setText("Doğrulama Kodunuz: " + otp);

                mailSender.send(message);
            } catch (MessagingException e) {
                throw new CustomException(HttpStatus.NOT_FOUND,"E-posta gönderilirken hata oluştu!");
            }
        }
    }
