package domain.account;

import java.time.Instant;
import java.util.Objects;

public final class Movement {

    private final Instant occurredAt;
    private final MovementType type;
    private final long amount; // en centavos (recomendado)
    private final long resultingBalance;

    public Movement(Instant occurredAt, MovementType type, long amount, long resultingBalance) {
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt");
        this.type = Objects.requireNonNull(type, "type");
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
        this.amount = amount;
        this.resultingBalance = resultingBalance;
    }

    public Instant getOccurredAt() { return occurredAt; }
    public MovementType getType() { return type; }
    public long getAmount() { return amount; }
    public long getResultingBalance() { return resultingBalance; }
}
