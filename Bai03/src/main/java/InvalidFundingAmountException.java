import java.util.Locale;

/**
 * Ngoại lệ được ném ra khi số tiền giao dịch không hợp lệ (ví dụ: số âm).
 */
public class InvalidFundingAmountException extends BankException {
    public InvalidFundingAmountException(double amount) {
        super("Số tiền không hợp lệ: $" + String.format(Locale.US, "%.2f", amount));
    }
}