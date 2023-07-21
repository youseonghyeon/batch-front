package com.example.batchfront.controller;

import com.example.batchfront.dto.BatchConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BatchController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @PostMapping("/start-batch")
    public void startBatch(@RequestBody Object obj) {
        try {
            rabbitTemplate.convertAndSend("batch-handler", "#", objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
