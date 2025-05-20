package com.bitirmeproje.service.auth;

import com.bitirmeproje.dto.auth.LoginDto;
import com.bitirmeproje.dto.user.RegisterDto;
import com.bitirmeproje.model.User;

public interface IAuthService {

    void registerUser(RegisterDto user);

    String login(LoginDto loginDto);

    void verifyOtpAndRegister(String email, String otp);

    void logout();
}
