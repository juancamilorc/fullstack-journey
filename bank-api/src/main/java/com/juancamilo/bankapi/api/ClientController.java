package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.api.dto.AccountResponse;
import com.juancamilo.bankapi.api.dto.ClientResponse;
import com.juancamilo.bankapi.api.dto.CreateClientRequest;
import com.juancamilo.bankapi.storage.jpa.*;
import domain.client.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.juancamilo.bankapi.storage.InMemoryAccountStore;
import com.juancamilo.bankapi.api.dto.CreateAccountRequest;
import domain.account.AccountType;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final JpaClientStore store;

    private final ClientRepository clientRepo;
    private final AccountRepository accountRepo;

    public ClientController(JpaClientStore store, InMemoryAccountStore accountStore, ClientRepository clientStore, AccountRepository accountRepository) {
        this.store = store;
        this.clientRepo = clientStore;
        this.accountRepo = accountRepository;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody CreateClientRequest req) {

        if (store.existsById(req.id())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }

        Client client = new Client(req.id(), req.name(), req.document());
        store.save(client);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ClientResponse(client.getId(), client.getName(), client.getDocument())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable String id) {

        Client client = store.findById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                new ClientResponse(
                        client.getId(),
                        client.getName(),
                        client.getDocument()
                )
        );
    }

    @PostMapping("/{clientId}/accounts")
    public ResponseEntity<?> createAccount(@PathVariable String clientId,
                                           @RequestBody CreateAccountRequest req) {

        ClientEntity clientEntity = clientRepo.findById(clientId).orElse(null);
        if (clientEntity == null) return ResponseEntity.notFound().build();

        if (accountRepo.existsById(req.number())) {
            return ResponseEntity.status(409).body("account number already exists");
        }

        AccountType type;
        try {
            type = AccountType.valueOf(req.type());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("invalid account type");
        }

        AccountEntity entity = new AccountEntity(
                req.number(),
                type.name(),
                0L,
                clientEntity
        );

        accountRepo.save(entity);

        return ResponseEntity.status(201).body(
                new AccountResponse(
                        entity.getNumber(),
                        entity.getType(),
                        entity.getBalanceAmount(),
                        clientEntity.getId()
                )
        );
    }

    @GetMapping("/{clientId}/accounts")
    public ResponseEntity<?> getAccounts(@PathVariable String clientId) {

        if (!clientRepo.existsById(clientId)) {
            return ResponseEntity.notFound().build();
        }

        var accounts = accountRepo.findByClient_Id(clientId);

        var response = accounts.stream()
                .map(a -> new AccountResponse(
                        a.getNumber(),
                        a.getType(),
                        a.getBalanceAmount(),
                        clientId
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}