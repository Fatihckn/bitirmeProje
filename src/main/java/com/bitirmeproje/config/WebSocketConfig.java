package com.bitirmeproje.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // queue ekledik
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user"); // önemli: user-specific prefix
    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // ws://localhost:8080/ws
                .setAllowedOriginPatterns("*") // Güvenlik için productionda kısıtlarsın
                .withSockJS();
    }
}
