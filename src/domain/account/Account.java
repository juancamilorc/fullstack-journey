package domain.account;

import domain.client.Client;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Account {

    private final String number;
    private final AccountType type;
    private final Client owner;

    private long balance; // centavos
    private final List<Movement> movements = new ArrayList<>();

    public Account(String number, AccountType type, Client owner) {
        this.number = Objects.requireNonNull(number, "number");
        this.type = Objects.requireNonNull(type, "type");
        this.owner = Objects.requireNonNull(owner, "owner");
        this.balance = 0L;
    }

    public String getNumber() { return number; }
    public AccountType getType() { return type; }
    public Client getOwner() { return owner; }
    public long getBalance() { return balance; }

    public List<Movement> getMovements() {
        return Collections.unmodifiableList(movements);
    }

    public void deposit(long amount) {
        validateAmount(amount);

        balance += amount;
        recordMovement(MovementType.DEPOSITO, amount);
    }

    public void withdraw(long amount) {
        validateAmount(amount);
        if (amount > balance) {
            throw new IllegalStateException("insufficient funds");
        }

        balance -= amount;
        recordMovement(MovementType.RETIRO, amount);
    }

    private void validateAmount(long amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
    }

    private void recordMovement(MovementType type, long amount) {
        movements.add(new Movement(Instant.now(), type, amount, balance));
    }
}
