package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.api.dto.AccountResponse;
import com.juancamilo.bankapi.api.dto.MoneyRequest;
import com.juancamilo.bankapi.storage.jpa.AccountEntity;
import com.juancamilo.bankapi.storage.jpa.AccountRepository;
import com.juancamilo.bankapi.storage.jpa.MovementEntity;
import com.juancamilo.bankapi.storage.jpa.MovementRepository;
import domain.exception.InsufficientFundsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountRepository accountRepo;
    private final MovementRepository movementRepo;

    public AccountController(AccountRepository accountRepo, MovementRepository movementRepo) {
        this.accountRepo = accountRepo;
        this.movementRepo = movementRepo;
    }

    @PostMapping("/{number}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String number,
                                     @RequestBody MoneyRequest req) {

        AccountEntity acc = accountRepo.findById(number).orElse(null);
        if (acc == null) return ResponseEntity.notFound().build();

        long amount = req.amount();
        if (amount <= 0) return ResponseEntity.badRequest().body("amount must be > 0");

        acc.setBalanceAmount(acc.getBalanceAmount() + amount);
        accountRepo.save(acc);

        movementRepo.save(new MovementEntity(
                java.time.Instant.now(),
                "DEPOSITO",
                amount,
                acc.getBalanceAmount(),
                acc
        ));

        return ResponseEntity.ok(toResponse(acc));
    }

    @PostMapping("/{number}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String number,
                                      @RequestBody MoneyRequest req) {

        AccountEntity acc = accountRepo.findById(number).orElse(null);
        if (acc == null) return ResponseEntity.notFound().build();

        long amount = req.amount();
        if (amount <= 0) return ResponseEntity.badRequest().body("amount must be > 0");

        if (amount > acc.getBalanceAmount()) {
            throw new InsufficientFundsException(acc.getBalanceAmount(), amount);
        }

        acc.setBalanceAmount(acc.getBalanceAmount() - amount);
        accountRepo.save(acc);

        movementRepo.save(new MovementEntity(
                java.time.Instant.now(),
                "RETIRO",
                amount,
                acc.getBalanceAmount(),
                acc
        ));

        return ResponseEntity.ok(toResponse(acc));
    }

    private AccountResponse toResponse(AccountEntity acc) {
        return new AccountResponse(
                acc.getNumber(),
                acc.getType(),
                acc.getBalanceAmount(),
                acc.getClient().getId()
        );
    }
}