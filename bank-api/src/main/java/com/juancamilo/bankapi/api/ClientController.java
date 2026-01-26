package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.api.dto.AccountResponse;
import com.juancamilo.bankapi.api.dto.CreateClientRequest;
import com.juancamilo.bankapi.storage.InMemoryClientStore;
import domain.client.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.juancamilo.bankapi.storage.InMemoryAccountStore;
import com.juancamilo.bankapi.api.dto.CreateAccountRequest;
import domain.account.Account;
import domain.account.AccountType;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final InMemoryClientStore store;
    private final InMemoryAccountStore accountStore;

    public ClientController(InMemoryClientStore store, InMemoryAccountStore accountStore) {
        this.store = store;
        this.accountStore = accountStore;
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

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable String id) {

        Client client = store.findById(id);

        if (client == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        return ResponseEntity.ok(client); // 200
    }

    @PostMapping("/{clientId}/accounts")
    public ResponseEntity<?> createAccount(@PathVariable String clientId,
                                           @RequestBody CreateAccountRequest req) {

        Client client = store.findById(clientId);
        if (client == null) return ResponseEntity.notFound().build();

        if (accountStore.existsByNumber(req.number())) {
            return ResponseEntity.status(409).body("account number already exists");
        }

        AccountType type;
        try {
            type = AccountType.valueOf(req.type()); // espera "AHORROS" o "CORRIENTE"
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("invalid account type");
        }

        Account account = new Account(req.number(), type, client);
        client.addAccount(account);
        accountStore.save(account);

        return ResponseEntity.status(201).body(
                new AccountResponse(
                        account.getNumber(),
                        account.getType().name(),
                        account.getBalanceAmount(),
                        client.getId()
                )
        );
    }
}