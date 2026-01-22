package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.api.dto.CreateClientRequest;
import com.juancamilo.bankapi.storage.InMemoryClientStore;
import domain.client.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final InMemoryClientStore store;

    public ClientController(InMemoryClientStore store) {
        this.store = store;
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody CreateClientRequest req) {

        if (store.existsById(req.id())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }

        Client client = new Client(req.id(), req.name(), req.document());
        store.save(client);

        return ResponseEntity.status(HttpStatus.CREATED).body(client); // 201
    }
}