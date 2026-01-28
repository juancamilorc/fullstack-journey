package com.juancamilo.bankapi.storage.jpa;

import com.juancamilo.bankapi.storage.jpa.ClientEntity;
import com.juancamilo.bankapi.storage.jpa.ClientRepository;
import domain.client.Client;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaClientStore {

    private final ClientRepository repo;

    public JpaClientStore(ClientRepository repo) {
        this.repo = repo;
    }

    public Client save(Client client) {
        ClientEntity entity = new ClientEntity(
                client.getId(),
                client.getName(),
                client.getDocument()
        );

        repo.save(entity);
        return client;
    }

    public Client findById(String id) {
        Optional<ClientEntity> found = repo.findById(id);
        if (found.isEmpty()) return null;

        ClientEntity e = found.get();
        return new Client(e.getId(), e.getName(), e.getDocument());
    }

    public boolean existsById(String id) {
        return repo.existsById(id);
    }
}
