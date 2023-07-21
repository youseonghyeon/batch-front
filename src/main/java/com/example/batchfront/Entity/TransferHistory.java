package com.example.batchfront.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString(exclude = "dailySettlement")
@NoArgsConstructor
public class TransferHistory {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    private String fromUsername;

    private String toUsername;

    private int amount;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "daily_settlement_id")
    private DailySettlement dailySettlement;

}
