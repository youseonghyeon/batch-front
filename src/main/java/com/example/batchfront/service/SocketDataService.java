package com.example.batchfront.service;

import com.example.batchfront.Entity.TransferStatus;
import com.example.batchfront.config.socket.VueWebSocketHandler;
import com.example.batchfront.repository.DailySettlementRepository;
import com.example.batchfront.repository.SettlementRepository;
import com.example.batchfront.repository.TransferHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocketDataService {

    private final VueWebSocketHandler vueWebSocketHandler;

    private final SettlementRepository settlementRepository;
    private final DailySettlementRepository dailySettlementRepository;
    private final TransferHistoryRepository transferHistoryRepository;

    private final ObjectMapper objectMapper;

//    @Scheduled(fixedDelay = 5000) // 5초마다 상태 전송
    public void pingPong() throws JsonProcessingException {
        long settlementCount = settlementRepository.count();
        long dailySettlementCount = dailySettlementRepository.count();
        long historyCount = transferHistoryRepository.count();
        long successCount = transferHistoryRepository.countByStatus(TransferStatus.COMPLETED);
        long failedCount = transferHistoryRepository.countByStatus(TransferStatus.FAILED);
        long reattemptCount = transferHistoryRepository.countByStatus(TransferStatus.REATTEMPT);
        SocketData socketData = new SocketData(settlementCount, dailySettlementCount, historyCount, successCount, failedCount, reattemptCount);
        String text = objectMapper.writeValueAsString(socketData);
        vueWebSocketHandler.sendMessage(text);
    }

    @Data
    @AllArgsConstructor
    public static class SocketData {
        private long settlementCount;
        private long dailySettlementCount;
        private long historyCount;
        private long successCount;
        private long failedCount;
        private long reattemptCount;
    }
}
