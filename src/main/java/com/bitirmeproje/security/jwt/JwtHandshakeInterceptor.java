package com.bitirmeproje.security.jwt;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;

    public JwtHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        URI uri = request.getURI();
        String path = uri.getPath();

        // 🟡 SockJS info veya handshake endpoint'leri için doğrulama yapılmaz
        if (path.contains("/ws/info") || path.endsWith("/ws")) {
            System.out.println("🟡 SockJS ön bağlantı (/ws/info veya /ws) için token doğrulaması yapılmadı.");
            return true;
        }

        // 🔐 Token'ı query parametresinden al
        String token = null;
        if (uri.getQuery() != null) {
            token = Arrays.stream(uri.getQuery().split("&"))
                    .filter(q -> q.startsWith("token="))
                    .map(q -> q.substring("token=".length()))
                    .findFirst()
                    .orElse(null);
        }

        System.out.println("👉 Handshake sırasında alınan token: " + token);

        if (token == null || token.isBlank()) {
            System.out.println("❌ Token bulunamadı. WebSocket bağlantısı reddedildi.");
            return false;
        }

        try {
            String takmaAd = jwtUtil.extractTakmaAd(token);
            if (takmaAd != null && !takmaAd.isBlank()) {
                attributes.put("username", takmaAd);
                System.out.println("✅ Token geçerli. Kullanıcı: " + takmaAd);
                return true;
            } else {
                System.out.println("❌ Token geçersiz. WebSocket bağlantısı reddedildi.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Token çözümlemesi başarısız: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // işlem yok
    }
}
