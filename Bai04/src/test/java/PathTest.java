import org.testng.annotations.Test;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;

public class PathTest {

    @Test
    public void testBadFilePath() {
        String folder = "logs";
        String fileName = "app.log";

        Path safePath = Paths.get(folder, fileName);
        File file = safePath.toFile();

        // Test kiểm tra xem thư mục cha có đúng là "logs" không
        assertEquals("logs", file.getParent());
    }
}