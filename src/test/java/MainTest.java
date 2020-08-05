import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MainTest {

    @Test
    public void unZipFile() throws IOException {
        String path = Main.unZipFile("C:/Users/phil/Desktop/compressed.zip");
        Assert.assertEquals("C:/Users/phil/Desktop/", path);
    }

    @Test
    public void zipArchiver() throws IOException {
        String path = Main.zipArchiver("C:/Users/phil/Desktop/Txt.txt");
        Assert.assertEquals("C:/Users/phil/Desktop/compressed.zip", path);
    }
}