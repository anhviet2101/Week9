import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    // Khởi tạo Logger cho class này
    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    public void processTask(String taskId, int itemsCount) {
        // Sử dụng INFO cho các mốc quan trọng, thay vì System.out.println
        // Dấu {} sẽ tự động thay thế bằng giá trị của taskId và itemsCount
        logger.info("Bắt đầu thực thi nhiệm vụ: {}, tổng số lượng: {}", taskId, itemsCount);

        try {
            if (itemsCount < 0) {
                throw new IllegalArgumentException("Số lượng không thể là số âm!");
            }
            // Giả lập logic code
            logger.info("Nhiệm vụ {} hoàn thành xuất sắc.", taskId);

        } catch (Exception e) {
            // Sử dụng ERROR để bắt ngoại lệ. 
            // Truyền trực tiếp biến 'e' vào cuối để Logback in ra toàn bộ stack trace
            logger.error("Xảy ra lỗi nghiêm trọng khi xử lý nhiệm vụ: {}", taskId, e);
        }
    }
}