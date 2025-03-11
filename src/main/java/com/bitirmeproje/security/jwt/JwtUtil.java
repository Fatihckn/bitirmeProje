package com.bitirmeproje.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey; // application.properties içindeki değeri çeker

    // Örnek: 1 saat geçerli olsun (3600000 ms)
    private final long expirationTime = 60 * 60 * 1000;

    // Key oluştur (Base64 Decode ile güvenli hale getirildi)
    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Token üret
    public String generateToken(String email,String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        //claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // Token'da email bilgisini tutuyoruz
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Token geçerli mi kontrol et
    public boolean validateToken(String token, String email) {
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    // Token içinden email'i çek
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Token içinden Expiration bilgisini çek
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Claims üzerinden istediğin alanı çekmek için
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    // Token'dan tüm Claims'i çek
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // Güncellendi!
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Token süresi dolmuş mu?
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
