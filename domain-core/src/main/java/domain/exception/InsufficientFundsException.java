package domain.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(long balance, long amount) {
        super("insufficient funds: balance=" + balance + ", amount=" + amount);
    }
}