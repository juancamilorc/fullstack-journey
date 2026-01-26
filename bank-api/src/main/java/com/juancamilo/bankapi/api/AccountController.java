package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.api.dto.MoneyRequest;
import com.juancamilo.bankapi.storage.InMemoryAccountStore;
import domain.account.Account;
import domain.exception.InsufficientFundsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final InMemoryAccountStore accounts;

    public AccountController(InMemoryAccountStore accounts) {
        this.accounts = accounts;
    }

    @PostMapping("/{number}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable String number,
                                           @RequestBody MoneyRequest req) {

        Account account = accounts.findByNumber(number);
        if (account == null) return ResponseEntity.notFound().build();

        account.deposit(req.amount());
        return ResponseEntity.ok(account);
    }

    @PostMapping("/{number}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String number,
                                      @RequestBody MoneyRequest req) {

        Account account = accounts.findByNumber(number);
        if (account == null) return ResponseEntity.notFound().build();

        try {
            account.withdraw(req.amount());
            return ResponseEntity.ok(account);
        } catch (InsufficientFundsException e) {
            // 409 Conflict: regla de negocio
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}