package com.panpan.nestexpense.goal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "goal")

//object for goal record in database
public class Goal {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long goalID;
    @Column(unique = true) //Unique
    private BigDecimal goal;
    private BigDecimal currentBalance;
    private BigDecimal amountReachGoal;
    private Long userID;
    private String purpose;
}