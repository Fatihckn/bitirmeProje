package com.bitirmeproje.helper.user;

import com.bitirmeproje.model.User;
import com.bitirmeproje.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GetUserByToken {
    private final JwtUtil jwtUtil;
    private final FindUser<Integer> findUser;

    public GetUserByToken(JwtUtil jwtUtil,
                          @Qualifier("findUserById") FindUser<Integer> findUser) {
        this.jwtUtil = jwtUtil;
        this.findUser = findUser;
    }

    public User getUser() {return findUser.findUser(jwtUtil.extractUserId());}
}
