package com.example.batchfront.config.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class VueWebSocketConfig implements WebSocketConfigurer {

    private final VueWebSocketHandler vueWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(vueWebSocketHandler, "/was-socket").setAllowedOrigins("*");
    }
    /**
     * var socket = new WebSocket("ws://localhost:8080/websocket-endpoint");
     *
     * socket.onopen = function() {
     *     console.log("WebSocket 연결이 열렸습니다.");
     *     socket.send("Hello Server!");
     * };
     *
     * socket.onmessage = function(event) {
     *     console.log("서버로부터 메시지 수신: " + event.data);
     * };
     *
     * socket.onclose = function(event) {
     *     console.log("WebSocket 연결이 닫혔습니다.");
     * };
     */
}
