package com.example.batchfront.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private int price;

    private LocalDateTime createdAt;

}
