package com.panpan.nestexpense.budget;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "budget")

//object for budget record in database
public class Budget {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    @Column(unique = true) //Unique
    @Column(name = "budgetID")
    private Long budgetID;
    @Column(name = "amountSpent")
    private BigDecimal amountSpent;
    @Column(name = "userID")
    private Long userID;
    @Column(name = "category")
    private String category;
    @Column(name = "budget")
    private BigDecimal budget;
    @Column(name = "amountToBudget")
    private BigDecimal amountToBudget;
}
