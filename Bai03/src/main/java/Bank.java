import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho Ngân hàng, quản lý danh sách khách hàng.
 */
public class Bank {
    private static final Logger logger = LoggerFactory.getLogger(Bank.class);
    private static final String ID_REGEX = "\\d{9}";

    private List<Customer> customerList;

    public Bank() {
        this.customerList = new ArrayList<>();
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Đặt danh sách khách hàng cho ngân hàng.
     *
     * @param customerList Danh sách khách hàng mới.
     */
    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<>();
        } else {
            this.customerList = customerList;
        }
    }

    /**
     * Đọc danh sách khách hàng từ InputStream.
     *
     * @param inputStream Luồng dữ liệu đầu vào.
     */
    public void readCustomerList(InputStream inputStream) {
        logger.debug("Bắt đầu đọc dữ liệu khách hàng từ InputStream.");
        if (inputStream == null) {
            logger.warn("InputStream rỗng, hủy bỏ thao tác đọc.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Customer currentCustomer = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                int lastSpaceIndex = line.lastIndexOf(' ');
                if (lastSpaceIndex > 0) {
                    String token = line.substring(lastSpaceIndex + 1).trim();

                    if (token.matches(ID_REGEX)) {
                        String name = line.substring(0, lastSpaceIndex).trim();
                        currentCustomer = new Customer(Long.parseLong(token), name);
                        customerList.add(currentCustomer);
                        logger.info("Thêm khách hàng mới: {}", name);
                    } else if (currentCustomer != null) {
                        processAccountLine(line, currentCustomer);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Lỗi I/O khi đọc danh sách khách hàng: {}", e.getMessage(), e);
        } catch (NumberFormatException e) {
            logger.error("Lỗi định dạng số trong luồng dữ liệu: {}", e.getMessage(), e);
        }
    }

    private void processAccountLine(String line, Customer customer) {
        String[] parts = line.split("\\s+");
        if (parts.length >= 3) {
            long accountNumber = Long.parseLong(parts[0]);
            double balance = Double.parseDouble(parts[2]);

            if (Account.CHECKING_TYPE.equals(parts[1])) {
                customer.addAccount(new CheckingAccount(accountNumber, balance));
                logger.debug("Thêm tài khoản vãng lai cho khách hàng {}", customer.getIdNumber());
            } else if (Account.SAVINGS_TYPE.equals(parts[1])) {
                customer.addAccount(new SavingsAccount(accountNumber, balance));
                logger.debug("Thêm tài khoản tiết kiệm cho khách hàng {}", customer.getIdNumber());
            }
        }
    }

    /**
     * Lấy thông tin tất cả khách hàng sắp xếp theo ID.
     * Chuỗi chứa thông tin khách hàng.
     */
    public String getCustomersInfoByIdOrder() {
        List<Customer> sortedList = new ArrayList<>(customerList);
        sortedList.sort(Comparator.comparingLong(Customer::getIdNumber));
        return formatCustomerList(sortedList);
    }

    /**
     * Lấy thông tin tất cả khách hàng sắp xếp theo tên.
     * Chuỗi chứa thông tin khách hàng.
     */
    public String getCustomersInfoByNameOrder() {
        List<Customer> sortedList = new ArrayList<>(customerList);
        sortedList.sort(Comparator.comparing(Customer::getFullName)
                .thenComparingLong(Customer::getIdNumber));
        return formatCustomerList(sortedList);
    }

    private String formatCustomerList(List<Customer> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i).getCustomerInfo());
            if (i < list.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}