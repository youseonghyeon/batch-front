package com.example.batchfront.config.socket.front;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class VueWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> socketStore = new HashSet<>();
    private final ObjectMapper objectMapper;

    private String subject = "pending";
    private String detail = "pending";
    private List<String> logMessage = new ArrayList<>();

    @Scheduled(fixedDelay = 300000)
    public void cleanUpExpiredSessions() {
        socketStore.removeIf(session -> !session.isOpen());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("[Vue3] Created new connection: SocketSession[id:{}]", session.getId());
        socketStore.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("Received message: {}", message.getPayload());
        super.handleMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.debug("Closed connection: SocketSession[id:{}]", session.getId());
        socketStore.remove(session);
    }

    public void sendMessage(String message) {
        log.debug("Sending message: {}", message);
        socketStore.stream().filter(WebSocketSession::isOpen).forEach(session -> {
            log.debug("session={}, message={}", session, message);
            sendMessage(session, message);
        });
    }

    private void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("Failed to send message: {}", message, e);
        }
    }


    @Data
    @AllArgsConstructor
    static class VueSpec {
        private String subject;
        private String detail;
        private List<String> logMessage;
    }
}
