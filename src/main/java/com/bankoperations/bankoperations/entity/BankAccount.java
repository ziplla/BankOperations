package com.bankoperations.bankoperations.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private double balance;

}
