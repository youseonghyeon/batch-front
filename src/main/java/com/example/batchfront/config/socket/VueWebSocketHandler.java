package com.example.batchfront.config.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;


@Slf4j
@Component
public class VueWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> socketStore = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Created new connection: SocketSession[id:{}]", session.getId());
        socketStore.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("Received message: {}", message.getPayload());
        super.handleMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Closed connection: SocketSession[id:{}]", session.getId());
        socketStore.remove(session);
    }

    public void sendMessage(String message) {
        log.info("Sending message: {}", message);
        socketStore.forEach(session -> {
            try {
                log.info("session={}, message={}", session, message);
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                log.error("Failed to send message: {}", message, e);
            }
        });
    }
}
