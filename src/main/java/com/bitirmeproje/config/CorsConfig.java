package com.bitirmeproje.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Tüm endpointler için geçerli
                        .allowedOriginPatterns("*") // Sadece bu kaynağa izin ver
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Tüm HTTP metodlarına izin ver
                        .allowedHeaders("*") // Tüm başlıklara izin ver
                        .allowCredentials(true); // Kimlik doğrulama bilgilerine (cookies, authorization headers) izin ver
            }
        };
    }
}
