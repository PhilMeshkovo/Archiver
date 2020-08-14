import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 1 && args[0].endsWith(".zip")) {
            String unZipPath = ZipArchiver.extract(args[0]);
            System.out.println(unZipPath);
        }
        if (args.length == 1 && !args[0].endsWith(".zip")) {
            File file = new File(args[0]);
            if (file.isDirectory()) {
                String path = ZipArchiver.ZipDirectory(args[0]);
                System.out.println(path);
            } else {
                String path = ZipArchiver.zipArchiver(args[0]);
                System.out.println(path);
            }
        }
        if (args.length > 1 && !args[0].endsWith(".zip")) {
            List<String> files = Arrays.asList(args);
            String path = ZipArchiver.ZipMultipleFiles(files);
            System.out.println(path);
        } else {
            InputStream inputStream = System.in;
            byte[] pattern = fromHexString("50 4B");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String text = reader.readLine();
            byte[] buffer = text.getBytes();
            if (buffer[0] == pattern[0] && buffer[1] == pattern[1]) {
                File tempFile = File.createTempFile("hello", ".zip");

                try (FileWriter writer = new FileWriter(tempFile)) {
                    writer.write(text);
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
                String path = ZipArchiver.extract(tempFile.toString());
                System.out.println(path);
            } else {
                File tempFile = File.createTempFile("hello", ".txt");

                try (FileWriter writer = new FileWriter(tempFile)) {
                    writer.write(text);
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
                String path = ZipArchiver.zipArchiver(tempFile.toString());
                System.out.println(path);
            }
        }
    }

    public static byte[] fromHexString(String src) {
        byte[] biBytes = new BigInteger("10" + src.replaceAll("\\s", ""), 16).toByteArray();
        return Arrays.copyOfRange(biBytes, 1, biBytes.length);
    }
}
