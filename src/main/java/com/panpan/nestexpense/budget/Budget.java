package com.panpan.nestexpense.budget;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "budget")

public class Budget {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long budgetID;
    @Column(unique = true) //Unique
    private BigDecimal amountSpent;
    private Long userID;
    private String category;
}