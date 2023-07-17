package com.example.batchfront.config.socket;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BatchWebSocketConfig {

    private final BatchWebSocketHandler batchWebSocketHandler;
    private WebSocketSession batchSession = null;

    WebSocketClient client = new StandardWebSocketClient();

    /**
     * batch 서버와 연결을 유지하기 위한 스케줄러
     */
    @Scheduled(fixedDelay = 60000)
    public void myTask()  {
        if (batchSession == null || !batchSession.isOpen()) {
            log.info("연결을 시도 합니다.");
            batchSession = connect();
        }
    }

    private WebSocketSession connect()  {
        CompletableFuture<WebSocketSession> execute = client.execute(batchWebSocketHandler, "ws://localhost:8002/batch-socket");
        try {
            return execute.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("batch 서버에 연결하지 못했습니다.", e);
        }
        return null;
    }

}

