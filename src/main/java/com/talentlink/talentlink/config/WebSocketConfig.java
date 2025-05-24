package com.talentlink.talentlink.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 구독 경로와 전송 경로 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");              // 구독 경로: 메시지 수신
        config.setApplicationDestinationPrefixes("/pub"); // 발행 경로: 메시지 전송
    }

    // WebSocket 연결 주소 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")   // 클라이언트가 연결할 기본 WebSocket 주소
                .setAllowedOriginPatterns("*") // CORS 허용 (개발 단계용)
                .withSockJS();            // SockJS fallback 지원
    }
}
