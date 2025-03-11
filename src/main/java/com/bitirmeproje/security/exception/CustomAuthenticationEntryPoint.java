package com.bitirmeproje.security.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

//        System.out.println(">>> CustomAuthenticationEntryPoint devrede!");
        // Örneğin 401 döndürelim:
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

//        // AuthenticationException'dan gelen mesajı basitçe body'ye yazıyoruz
//        String errorMessage = authException.getMessage();
//        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");

        response.getWriter().write("{\"error\": \"Hatalı Token!\"}");
    }
}
