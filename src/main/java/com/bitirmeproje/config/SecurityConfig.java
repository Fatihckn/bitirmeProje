package com.bitirmeproje.config;

import com.bitirmeproje.security.exception.CustomAccessDeniedHandler;
import com.bitirmeproje.security.exception.CustomAuthenticationEntryPoint;
import com.bitirmeproje.security.jwt.JwtAuthenticationFilter;
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
    private final CustomAuthenticationEntryPoint customAuthEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomAuthenticationEntryPoint customAuthEntryPoint,
                          CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthEntryPoint = customAuthEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors->cors.configure(http))
                // CSRF devre dışı (Postman vb. testler için)
                .csrf(csrf -> csrf.disable())

                // Session'ı tamamen devre dışı bırakarak stateless bir yapı kuruyoruz
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Endpoint izinleri
                .authorizeHttpRequests(auth -> auth
                        // Auth işlemleri (JWT olmadan erişilebilir)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Şifre sıfırlama işlemleri (JWT olmadan erişilebilir)
                        .requestMatchers("/api/user/sifre-sifirla").permitAll()
                        .requestMatchers("/api/user/sifre-dogrula").permitAll()
                        .requestMatchers("/api/user/yeni-sifre-belirle").permitAll()

                        // Admin işlemleri (Admin yetkisi gerekli)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Kullanıcı işlemleri (JWT Token gerektirir)
                        .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/arama/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/gonderiBegeni/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/gonderi/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/mesaj/**").hasAnyRole("USER", "ADMIN")





                        // Diğer tüm endpoint'ler token gerektirir
                        .anyRequest().authenticated()
                )

                // Varsayılan Spring form login ekranını kapat
                .formLogin(form -> form.disable())

                // Basic Auth'u da kapat
                .httpBasic(httpBasic -> httpBasic.disable())

                        .exceptionHandling(ex -> ex
                                .authenticationEntryPoint(customAuthEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                        );

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
