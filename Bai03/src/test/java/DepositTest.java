import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositTest{
    @Test
    public void testDeposit() {
        Account acc = new CheckingAccount(12345, 100.0);
        acc.deposit(50.0);
        assertEquals(150, acc.getBalance());
        }
}