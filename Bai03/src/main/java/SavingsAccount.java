import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản tiết kiệm. Thực thi các quy định về giới hạn rút và số dư.
 */
public class SavingsAccount extends Account {
    private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

    private static final double MAX_WITHDRAW_LIMIT = 1000.0;
    private static final double MIN_BALANCE_LIMIT = 5000.0;

    public SavingsAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        logger.debug("Đang xử lý giao dịch nạp tiền tiết kiệm...");
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            Transaction transaction = new Transaction(
                    Transaction.TYPE_DEPOSIT_SAVINGS,
                    amount,
                    initialBalance,
                    finalBalance);
            addTransaction(transaction);
            logger.info("Nạp tiền thành công vào tài khoản tiết kiệm {}: +{}", getAccountNumber(), amount);
        } catch (BankException e) {
            logger.warn("Lỗi nạp tiền tài khoản tiết kiệm {}: {}", getAccountNumber(), e.getMessage());
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {
            if (amount > MAX_WITHDRAW_LIMIT) {
                throw new InvalidFundingAmountException(amount);
            }
            if (initialBalance - amount < MIN_BALANCE_LIMIT) {
                throw new InsufficientFundsException(amount);
            }

            doWithdrawing(amount);
            double finalBalance = getBalance();

            Transaction transaction = new Transaction(
                    Transaction.TYPE_WITHDRAW_SAVINGS,
                    amount,
                    initialBalance,
                    finalBalance);
            addTransaction(transaction);

            logger.info("Rút tiền thành công từ tài khoản tiết kiệm {}. Số dư còn lại: {}",
                    getAccountNumber(), finalBalance);
        } catch (BankException e) {
            logger.warn("Lỗi rút tiền tài khoản tiết kiệm {}: {}", getAccountNumber(), e.getMessage());
        }
    }
}