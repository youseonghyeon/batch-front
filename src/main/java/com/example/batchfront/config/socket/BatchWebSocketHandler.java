package com.example.batchfront.config.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@Component
public class BatchWebSocketHandler extends TextWebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Created new connection: BatchSocketSession[id:{}]", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("Received message: {}", message.getPayload());

        // TODO batch 서버에서 받은 데이터 처리 로직


        super.handleMessage(session, message);
    }

}
