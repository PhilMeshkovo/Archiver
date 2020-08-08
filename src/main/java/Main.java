import java.io.*;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].endsWith(".zip")) {
            String unZipPath = ZipArchiver.unZipFile(args[0]);
            System.out.println(unZipPath);
        }
        if (args.length > 0 && !args[0].endsWith(".zip")) {
            String path = ZipArchiver.zipArchiver(args[0]);
            System.out.println(path);
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String text = reader.readLine();
            boolean isZipped = new ZipInputStream(System.in).getNextEntry() != null;
            if (!isZipped) {
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
            if (isZipped) {
                File tempFile = File.createTempFile("hello", ".zip");

                try (FileWriter writer = new FileWriter(tempFile)) {
                    writer.write(text);
                    writer.flush();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
                String path = ZipArchiver.unZipFile(tempFile.toString());
                System.out.println(path);
            }

        }
    }
}
