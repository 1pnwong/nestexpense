package com.panpan.nestexpense.income;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "income")

public class Income {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long incomeID;
    @Column(unique = true) //Unique
    private BigDecimal amount;
    private Long userID;
    private String source;
    private LocalDateTime dateIncome;
}