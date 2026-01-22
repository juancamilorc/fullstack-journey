package com.juancamilo.bankapi.service;

import domain.account.Account;
import domain.account.AccountType;
import domain.client.Client;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BankService {

    private final Map<String, Client> clients = new ConcurrentHashMap<>();
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public Client createClient(String id, String name, String document) {
        Client client = new Client(id, name, document);
        clients.put(id, client);
        return client;
    }

    public Account createAccount(String number, AccountType type, String clientId) {
        Client owner = clients.get(clientId);
        if (owner == null) {
            throw new IllegalArgumentException("client not found: " + clientId);
        }

        Account account = new Account(number, type, owner);
        owner.addAccount(account);
        accounts.put(number, account);
        return account;
    }

    public Account getAccount(String number) {
        Account account = accounts.get(number);
        if (account == null) {
            throw new IllegalArgumentException("account not found: " + number);
        }
        return account;
    }
}
