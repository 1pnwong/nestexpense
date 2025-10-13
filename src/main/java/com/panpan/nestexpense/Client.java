package com.panpan.nestexpense;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")

public class Client {
    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long userID;
    @Column(unique = true) //Unique
    private String email;
    private String password;
}
