package com.bitirmeproje.config;

import com.bitirmeproje.security.jwt.JwtHandshakeInterceptor;
import com.bitirmeproje.security.jwt.JwtUtil;
import com.bitirmeproje.security.jwt.CustomHandshakeHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;

    public WebSocketConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        System.out.println("📡 WebSocketConfig -> Mesaj broker yapılandırması başlatıldı.");
        config.enableSimpleBroker("/topic", "/queue"); // kullanıcıya özel "/queue"
        config.setApplicationDestinationPrefixes("/app"); // client -> server
        config.setUserDestinationPrefix("/user"); // server -> user
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("🔗 WebSocketConfig -> STOMP endpoint kaydı yapılıyor (/ws)");
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .addInterceptors(new JwtHandshakeInterceptor(jwtUtil))
                .setHandshakeHandler(new CustomHandshakeHandler());
    }
}
