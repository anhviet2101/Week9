import org.testng.annotations.Test;
import java.io.File;
import static org.testng.Assert.assertEquals;

public class PathTest {

    @Test
    public void testBadFilePath() {
        String folder = "logs";
        String fileName = "app.log";

        // Cố tình nối chuỗi dùng dấu gạch chéo ngược của Windows (\)
        String badPath = folder + "\\" + fileName;

        File file = new File(badPath);

        // Test kiểm tra xem thư mục cha có đúng là "logs" không
        assertEquals("logs", file.getParent());
    }
}