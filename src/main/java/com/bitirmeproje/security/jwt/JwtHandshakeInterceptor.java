package com.bitirmeproje.security.jwt;

import com.bitirmeproje.exception.CustomException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;

    // JwtUtil enjekte ediliyor
    public JwtHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest servletHttpRequest = servletRequest.getServletRequest();
            String token = servletHttpRequest.getHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                try {
                    token = token.substring(7); // "Bearer " kısmını çıkar
                    String username = jwtUtil.extractEmail(token); // email = subject
                    if (username != null) {
                        attributes.put("username", username); // Principal için bağlama aktar
                    } else {
                        throw new CustomException(HttpStatus.UNAUTHORIZED, "JWT geçersiz!");
                    }
                } catch (JwtException e) {
                    throw new CustomException(HttpStatus.FORBIDDEN, "JWT doğrulanamadı: " + e.getMessage());
                }
            } else {
                throw new CustomException(HttpStatus.UNAUTHORIZED, "JWT başlığı bulunamadı!");
            }
        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}

