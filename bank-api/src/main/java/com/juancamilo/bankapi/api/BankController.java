package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.service.BankService;
import domain.account.Account;
import domain.account.AccountType;
import domain.client.Client;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/clients")
    public Client createClient(@RequestBody CreateClientRequest req) {
        return bankService.createClient(req.id(), req.name(), req.document());
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody CreateAccountRequest req) {
        return bankService.createAccount(req.number(), req.type(), req.clientId());
    }

    @GetMapping("/accounts/{number}")
    public Account getAccount(@PathVariable String number) {
        return bankService.getAccount(number);
    }

    // --- DTOs (por ahora dentro del controller para hacerlo simple) ---
    public record CreateClientRequest(String id, String name, String document) {}
    public record CreateAccountRequest(String number, AccountType type, String clientId) {}
}