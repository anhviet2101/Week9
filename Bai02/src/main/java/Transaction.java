import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Đại diện cho một giao dịch trong hệ thống.
 */
public class Transaction {
    private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

    public static final int TYPE_DEPOSIT_CHECKING = 1;
    public static final int TYPE_WITHDRAW_CHECKING = 2;
    public static final int TYPE_DEPOSIT_SAVINGS = 3;
    public static final int TYPE_WITHDRAW_SAVINGS = 4;

    private int type;
    private double amount;
    private double initialBalance;
    private double finalBalance;

    public Transaction(int type, double amount, double initialBalance, double finalBalance) {
        this.type = type;
        this.amount = amount;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
    }

    // Các hàm Getters và Setters
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public double getInitialBalance() { return initialBalance; }
    public void setInitialBalance(double initialBalance) { this.initialBalance = initialBalance; }
    public double getFinalBalance() { return finalBalance; }
    public void setFinalBalance(double finalBalance) { this.finalBalance = finalBalance; }

    /**
     * Lấy tên kiểu giao dịch dựa trên mã kiểu.
     *
     * @param type Mã kiểu giao dịch.
     * @return Tên kiểu giao dịch dưới dạng chuỗi.
     */
    public static String getTypeString(int type) {
        switch (type) {
            case TYPE_DEPOSIT_CHECKING:
                return "Nạp tiền vãng lai";
            case TYPE_WITHDRAW_CHECKING:
                return "Rút tiền vãng lai";
            case TYPE_DEPOSIT_SAVINGS:
                return "Nạp tiền tiết kiệm";
            case TYPE_WITHDRAW_SAVINGS:
                return "Rút tiền tiết kiệm";
            default:
                return "Không rõ";
        }
    }

    /**
     * Lấy chuỗi tóm tắt thông tin giao dịch.
     *
     * @return Chuỗi tóm tắt giao dịch.
     */
    public String getTransactionSummary() {
        logger.debug("Tạo tóm tắt cho giao dịch kiểu: {}", type);
        String transactionType = getTypeString(type);

        return String.format(Locale.US,
                "- Kiểu giao dịch: %s. Số dư ban đầu: $%.2f. Số tiền: $%.2f. Số dư cuối: $%.2f.",
                transactionType, initialBalance, amount, finalBalance);
    }
}