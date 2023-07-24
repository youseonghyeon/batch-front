package com.example.batchfront.dto;

import lombok.Data;

@Data
public class SocketMessage {

    private String type;
    private String subject;
    private String detail;
}
