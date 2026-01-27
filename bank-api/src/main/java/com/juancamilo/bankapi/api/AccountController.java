package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.api.dto.AccountResponse;
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
    public ResponseEntity<?> deposit(@PathVariable String number,
                                     @RequestBody MoneyRequest req) {

        // 1) Validación de request
        if (req == null || req.amount() <= 0) {
            return ResponseEntity.badRequest().body("amount must be > 0");
        }

        // 2) Buscar cuenta
        Account account = accounts.findByNumber(number);
        if (account == null) return ResponseEntity.notFound().build();

        // 3) Acción de dominio
        account.deposit(req.amount());

        // 4) Respuesta limpia (sin loop)
        return ResponseEntity.ok(toResponse(account));
    }

    @PostMapping("/{number}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String number,
                                      @RequestBody MoneyRequest req) {

        if (req == null || req.amount() <= 0) {
            return ResponseEntity.badRequest().body("amount must be > 0");
        }

        Account account = accounts.findByNumber(number);
        if (account == null) return ResponseEntity.notFound().build();

        try {
            account.withdraw(req.amount());
            return ResponseEntity.ok(toResponse(account));
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getNumber(),
                account.getType().name(),
                account.getBalanceAmount(),
                account.getOwner().getId()
        );
    }
}