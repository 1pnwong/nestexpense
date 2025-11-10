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
    @Column(unique = true) //Unique
    private Long budgetID;
    private BigDecimal amountSpent;
    private Long userID;
    private String category;
    private BigDecimal budget;
    private BigDecimal amountToBudget;
}