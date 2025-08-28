package com.kyj.fmk.config;



import com.kyj.fmk.interceptor.JwtHandshakeInterceptor;
import com.kyj.fmk.interceptor.StompInterceptor;

import com.kyj.fmk.sec.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

/**
 * 2025-08-28
 * @author 김용준
 * 웹소켓 설정을 하는 클래스
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JWTUtil jwtUtil;
    private final RedisTemplate<String,Object> redisTemplate;

    /**
     * 구독 엔드포인트 설정
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트가 구독할 prefix
        config.enableSimpleBroker("/topic", "/queue");

        // 클라이언트가 보낼 때 붙이는 prefix (Controller @MessageMapping과 연결)
        config.setApplicationDestinationPrefixes("/app");

        // 1:1 메시징을 위한 prefix (/user/queue/...)
        config.setUserDestinationPrefix("/user");
    }

    /**
     * 연결 엔드포인트 설정
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // JS에서 연결할 엔드포인트
        registry.addEndpoint("/api/v1/realtime/ws")
                .setAllowedOriginPatterns("*") // CORS 허용
                .addInterceptors(new JwtHandshakeInterceptor(jwtUtil,redisTemplate)) // JWT검증
                .withSockJS();               // SockJS fallback 지원
    }

    /**
     * Stomp 인터셉터 등록
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new StompInterceptor());
    }

}


