package com.bitirmeproje.security.jwt;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        Object usernameObj = attributes.get("username");
        System.out.println("🧩 CustomHandshakeHandler -> Kullanıcı atanıyor: " + usernameObj);

        if (usernameObj instanceof String username && username != null && !username.isBlank()) {
            System.out.println("✅ Kullanıcı Principal olarak atandı: " + username);
            return () -> username;
        }

        // ❌ Eğer kullanıcı bilgisi yoksa, bağlantıyı reddet
        System.out.println("❌ Kullanıcı atanamadı. Principal null.");
        return null;
    }
}
