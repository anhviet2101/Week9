import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositTest{
    @Test
    public void testDeposit() {
        Account acc = new CheckingAccount(12345, 100.0);
        acc.deposit(50.0);
        // Cố tình sửa số tiền kỳ vọng sai để test bị tạch
        assertEquals(9999.0, acc.getBalance());
        }
}