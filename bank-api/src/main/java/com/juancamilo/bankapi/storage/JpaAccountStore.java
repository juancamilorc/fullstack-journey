package com.juancamilo.bankapi.storage;

import com.juancamilo.bankapi.storage.jpa.AccountEntity;
import com.juancamilo.bankapi.storage.jpa.AccountRepository;
import domain.account.Account;
import domain.account.AccountType;
import domain.client.Client;
import org.springframework.stereotype.Component;

@Component
public class JpaAccountStore {

    private final AccountRepository repo;

    public JpaAccountStore(AccountRepository repo) {
        this.repo = repo;
    }

    public boolean existsByNumber(String number) {
        return repo.existsById(number);
    }

    public void save(AccountEntity entity) {
        repo.save(entity);
    }

    public AccountEntity findEntityByNumber(String number) {
        return repo.findById(number).orElse(null);
    }

    // (Luego hacemos mapping a Domain cuando lo necesitemos)
}