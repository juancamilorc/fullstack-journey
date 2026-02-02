package com.juancamilo.bankapi.storage.jpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @Column(nullable = false, length = 50)
    private String number;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false)
    private long balanceAmount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    protected AccountEntity() {}

    public AccountEntity(String number, String type, long balanceAmount, ClientEntity client) {
        this.number = number;
        this.type = type;
        this.balanceAmount = balanceAmount;
        this.client = client;
    }

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<MovementEntity> movements = new java.util.ArrayList<>();

    public java.util.List<MovementEntity> getMovements() { return movements; }

    public String getNumber() { return number; }
    public String getType() { return type; }
    public long getBalanceAmount() { return balanceAmount; }
    public ClientEntity getClient() { return client; }

    public void setBalanceAmount(long balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}