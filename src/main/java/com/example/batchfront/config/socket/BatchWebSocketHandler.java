package com.example.batchfront.config.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;


@Slf4j
@Component
@RequiredArgsConstructor
public class BatchWebSocketHandler extends TextWebSocketHandler {

    private final VueWebSocketHandler vueWebSocketHandler;
    private final ObjectMapper objectMapper;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Created new connection: BatchSocketSession[id:{}]", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws JsonProcessingException {
        SockMessageSpec sockMessageSpec = objectMapper.readValue(message.getPayload().toString(), SockMessageSpec.class);
        vueWebSocketHandler.sendMessage(objectMapper.writeValueAsString(sockMessageSpec));
    }

    @Data
    private static class SockMessageSpec {
        private String type;
        private String subject;
        private String detail;
    }

}
