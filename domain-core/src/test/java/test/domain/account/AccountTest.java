package domain.account;

import domain.client.Client;
import domain.exception.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void deposit_shouldIncreaseBalance() {
        Client client = new Client("1", "Juan", "123");
        Account account = new Account("ACC-1", AccountType.AHORROS, client);

        account.deposit(100);

        assertEquals(100, account.getBalanceAmount());
    }

    @Test
    void withdraw_shouldDecreaseBalance() {
        Client client = new Client("1", "Juan", "123");
        Account account = new Account("ACC-1", AccountType.AHORROS, client);

        account.deposit(200);
        account.withdraw(50);

        assertEquals(150, account.getBalanceAmount());
        assertEquals(2, account.getMovements().size());
        assertEquals(MovementType.RETIRO, account.getMovements().get(1).getType());
        assertEquals(150, account.getMovements().get(1).getResultingBalance());
    }

    @Test
    void withdraw_shouldThrow_whenInsufficientFunds() {
        Client client = new Client("1", "Juan", "123");
        Account account = new Account("ACC-1", AccountType.AHORROS, client);

        InsufficientFundsException ex = assertThrows(
                InsufficientFundsException.class,
                () -> account.withdraw(10)
        );

        assertTrue(ex.getMessage().startsWith("insufficient funds"));
        assertEquals(0, account.getBalanceAmount());
        assertEquals(0, account.getMovements().size());
    }

    @Test
    void deposit_shouldThrow_whenAmountIsZeroOrNegative() {
        Client client = new Client("1", "Juan", "123");
        Account account = new Account("ACC-1", AccountType.AHORROS, client);

        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-1));

        assertEquals(0, account.getBalanceAmount());
        assertEquals(0, account.getMovements().size());
    }
}
