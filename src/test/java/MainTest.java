import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MainTest {

    @Test
    public void unZipFile() throws IOException {
        String path = Main.unZipFile("src/main/resources/compressed.zip");
        Assert.assertEquals("src/main/resources/unzipTest", path);
    }

    @Test
    public void zipArchiver() throws IOException {
        String path = Main.zipArchiver("src/main/resources/Новый текстовый документ.txt");
        Assert.assertEquals("src/main/resources/compressed.zip", path);
    }
}