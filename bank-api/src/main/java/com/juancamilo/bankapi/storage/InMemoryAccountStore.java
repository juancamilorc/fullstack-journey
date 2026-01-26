package com.juancamilo.bankapi.storage;

import domain.account.Account;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAccountStore {

    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public Account save(Account account) {
        accounts.put(account.getNumber(), account);
        return account;
    }

    public Account findByNumber(String number) {
        return accounts.get(number);
    }

    public boolean existsByNumber(String number) {
        return accounts.containsKey(number);
    }
}