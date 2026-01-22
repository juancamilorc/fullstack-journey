package domain.money;

import java.util.Objects;

public final class Money {

    private final long amount; // centavos

    private Money(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("money amount must be >= 0");
        }
        this.amount = amount;
    }

    public static Money of(long amount) {
        return new Money(amount);
    }

    public long getAmount() {
        return amount;
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, "other");
        return new Money(this.amount + other.amount);
    }

    public Money subtract(Money other) {
        Objects.requireNonNull(other, "other");
        if (other.amount > this.amount) {
            throw new IllegalArgumentException("cannot subtract more money than available");
        }
        return new Money(this.amount - other.amount);
    }

    public boolean isGreaterThan(Money other) {
        Objects.requireNonNull(other, "other");
        return this.amount > other.amount;
    }
}