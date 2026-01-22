package com.juancamilo.bankapi.storage;

import domain.client.Client;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryClientStore {

    private final Map<String, Client> clients = new ConcurrentHashMap<>();

    public Client save(Client client) {
        clients.put(client.getId(), client);
        return client;
    }

    public Client findById(String id) {
        return clients.get(id);
    }

    public boolean existsById(String id) {
        return clients.containsKey(id);
    }
}