package com.example.pingpong.websocket;

import com.example.pingpong.global.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameHandler gameHandler;
    private final HttpSessionIdHandshakeInterceptor interceptor = new HttpSessionIdHandshakeInterceptor();

    /**
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/socket/config/annotation/WebSocketHandlerRegistration.html#withSockJS()
     * @param registry
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gameHandler, "/websocket/game")
                .setAllowedOriginPatterns(Global.ALLOW_ORIGIN_PATTERNS)
                .withSockJS()
                .setInterceptors(interceptor);
    }

}
