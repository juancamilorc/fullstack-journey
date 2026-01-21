package domain.account;

import domain.client.Client;
import domain.exception.InsufficientFundsException;
import domain.money.Money;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Account {

    private final String number;
    private final AccountType type;
    private final Client owner;

    private Money balance;// centavos
    private final List<Movement> movements = new ArrayList<>();

    public Account(String number, AccountType type, Client owner) {
        this.number = Objects.requireNonNull(number, "number").trim();
        if (this.number.isEmpty()) throw new IllegalArgumentException("number must not be blank");
        this.type = Objects.requireNonNull(type, "type");
        this.owner = Objects.requireNonNull(owner, "owner");
        this.balance = Money.of(0);
    }

    public String getNumber() { return number; }
    public AccountType getType() { return type; }
    public Client getOwner() { return owner; }
    public Money getBalance() { return balance; }

    public List<Movement> getMovements() {
        return Collections.unmodifiableList(movements);
    }

    public void deposit(long amount) {
        validateAmount(amount);

        balance = balance.add(Money.of(amount));
        recordMovement(MovementType.DEPOSITO, amount);
    }

    public void withdraw(long amount) {
        Money withdrawal = Money.of(amount);

        if (withdrawal.getAmount() > balance.getAmount()) {
            throw new InsufficientFundsException(balance.getAmount(), amount);
        }

        balance = balance.subtract(withdrawal);
        recordMovement(MovementType.RETIRO, amount);
    }

    private void validateAmount(long amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
    }

    private void recordMovement(MovementType type, long amount) {
        movements.add(new Movement(Instant.now(), type, amount, balance.getAmount()));
    }

    public Movement getLastMovement() {
        if (movements.isEmpty()) return null;
        return movements.get(movements.size() - 1);
    }
    public long getBalanceAmount() {
        return balance.getAmount();
    }
}
