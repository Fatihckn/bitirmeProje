package com.bitirmeproje.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // "Bearer " ile başlayan token var mı?
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

        // "Bearer " ile başlayan token var mı?
            try {
                // Token'ı parse edelim
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtUtil.getSigningKey())  // getSigningKey() -> public
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                System.out.println("token= "+token);
                String email = claims.getSubject();
                String role  = claims.get("role", String.class);
                System.out.println("JAF: calisti2");

                if (email == null || role == null) {
                    System.out.println("JAF: calisti3");
                    throw new BadCredentialsException("Token formatı geçersiz: email veya role yok!");
                }

                // Süre dolmuş mu?
                Date expiration = claims.getExpiration();
                if (expiration.before(new Date())) {
                    System.out.println("JAF: calisti3");
                    throw new BadCredentialsException("Token süresi dolmuş!");
                }

                // Geçerliyse SecurityContext'e yükleyelim
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority("ROLE_" + role));

                User principal = new User(email, "", authorities);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(principal, null, authorities);

                // SecurityContext'e set
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                // parseClaimsJws'tan dönen hatalar (imza hatası, expiration, vb.)
                throw new BadCredentialsException("Geçersiz token: " + e.getMessage(), e);
            }
        }

        // Devam
        filterChain.doFilter(request, response);
    }
}
