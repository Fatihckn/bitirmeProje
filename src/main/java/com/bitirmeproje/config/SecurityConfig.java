package com.bitirmeproje.config;

import com.bitirmeproje.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF devre dışı (Postman vb. testler için)
                .csrf(csrf -> csrf.disable())

                // Session'ı tamamen devre dışı bırakarak stateless bir yapı kuruyoruz
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Endpoint izinleri
                .authorizeHttpRequests(auth -> auth
                        // Bu endpoint'ler token olmadan erişilebilir
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        // Diğer tüm endpoint'ler token gerektirir
                        .anyRequest().authenticated()
                )

                // Varsayılan Spring form login ekranını kapat
                .formLogin(form -> form.disable())

                // Basic Auth'u da kapat
                .httpBasic(httpBasic -> httpBasic.disable());

        // JWT doğrulama filtresini ekle, UsernamePasswordAuthenticationFilter'dan önce çalışsın
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Şifreleri BCrypt ile hash'lemek için
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
