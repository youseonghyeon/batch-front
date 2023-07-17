package com.example.batchfront.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BatchConfiguration implements Serializable {
    private int mockSize;
    private int chunkSize;
    private String targetDate;
}
