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
        String nickname = getNickname((ServletServerHttpRequest) request);
        attributes.put("nickname", nickname);

        HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession();
        if (session.getAttribute("ballPosX") != "") {
            Integer ballPosX = Integer.parseInt(String.valueOf(session.getAttribute("ballPosX")));
            Integer ballPosY = Integer.parseInt(String.valueOf(session.getAttribute("ballPosY")));
            attributes.put("ballPosX", ballPosX);
            attributes.put("ballPosY", ballPosY);
        }
        return true;
    }

    private static String getNickname(ServletServerHttpRequest request) {
        HttpSession session = request.getServletRequest().getSession();
        return String.valueOf(session.getAttribute("user"));
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
