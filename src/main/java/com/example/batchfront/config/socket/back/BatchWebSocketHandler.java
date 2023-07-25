package com.example.batchfront.config.socket.back;

import com.example.batchfront.config.socket.front.VueWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@Component
@RequiredArgsConstructor
public class BatchWebSocketHandler extends TextWebSocketHandler {

    private final VueWebSocketHandler vueWebSocketHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Created new connection: BatchSocketSession[id:{}]", session.getId());
    }

    /**
     * batch 서버에서 받은 메시지를 vue 서버로 전달
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        vueWebSocketHandler.sendMessage(message.getPayload().toString());
    }

}
