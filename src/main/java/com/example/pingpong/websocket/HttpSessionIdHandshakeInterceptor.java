package com.example.pingpong.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            String nickname = getNickname((ServletServerHttpRequest) request);
            attributes.put("nickname", nickname);
        }
        return true;
    }

    private static String getNickname(ServletServerHttpRequest request) {
        HttpSession session = request.getServletRequest().getSession();
        Object userAttribute = session.getAttribute("user");
        if (userAttribute != null) {
            return String.valueOf(userAttribute);
        } else {
            return "";
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
