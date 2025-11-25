package com.panpan.nestexpense.expense;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "expense")
@Access(AccessType.FIELD)

//object for expense record in database
public class Expense {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long expenseID;
    @Column(unique = true) //Unique
    private BigDecimal amount;
    private Long userID;
    private String sentToWhere;
    @Column(name = "dateExpense")
    private LocalDateTime dateExpense;
    private String category;
}