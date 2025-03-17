package com.bitirmeproje.service.auth;

import com.bitirmeproje.dto.auth.LoginDto;
import com.bitirmeproje.model.User;

public interface IAuthService {

    void registerUser(User user);

    String login(LoginDto loginDto);
}
