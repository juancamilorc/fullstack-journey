package com.juancamilo.bankapi.storage.jpa;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "movements")
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant occurredAt;

    @Column(nullable = false, length = 20)
    private String type; // DEPOSITO / RETIRO

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private long resultingBalance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_number", nullable = false)
    private AccountEntity account;

    protected MovementEntity() {}

    public MovementEntity(Instant occurredAt,
                          String type,
                          long amount,
                          long resultingBalance,
                          AccountEntity account) {
        this.occurredAt = occurredAt;
        this.type = type;
        this.amount = amount;
        this.resultingBalance = resultingBalance;
        this.account = account;
    }

    public Long getId() { return id; }
    public Instant getOccurredAt() { return occurredAt; }
    public String getType() { return type; }
    public long getAmount() { return amount; }
    public long getResultingBalance() { return resultingBalance; }
    public AccountEntity getAccount() { return account; }
}