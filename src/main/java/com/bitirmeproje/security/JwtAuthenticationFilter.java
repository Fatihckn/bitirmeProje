package com.bitirmeproje.security;

import com.bitirmeproje.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("Bearer " + authHeader);
            String token = authHeader.substring(7);

            // Token'dan email'i çek
            String email = jwtUtil.extractEmail(token);
            System.out.println("email: " + email);

            // Token geçerli mi kontrol et
            if (email != null && jwtUtil.validateToken(token, email)) {
                System.out.println("valid");

                // Kullanıcıyı temsil eden UserDetails nesnesi oluştur
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                        email, "", Collections.emptyList()
                );

                // Authentication nesnesi oluştur ve SecurityContext'e set et
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext'e Authentication ekleyerek kullanıcının giriş yaptığını bildir
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            else {
                // Token geçersizse 401 döndür
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // Filtreyi durdur
            }
        }
        // Bearer yoksa veya yoksa login/register endpointine gidebilir, normal devam etsin
        filterChain.doFilter(request, response);
    }
}
