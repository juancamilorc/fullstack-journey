package com.juancamilo.bankapi.storage.jpa;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @Column(nullable = false, length = 50)
    private String id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 50)
    private String document;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountEntity> accounts = new ArrayList<>();

    protected ClientEntity() {}

    public ClientEntity(String id, String name, String document) {
        this.id = id;
        this.name = name;
        this.document = document;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDocument() { return document; }
    public List<AccountEntity> getAccounts() { return accounts; }
}