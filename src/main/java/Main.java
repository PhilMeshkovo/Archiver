
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].endsWith(".zip")) {
            String unZipPath = unZipFile(args[0]);
            System.out.println(unZipPath);
        }
        if (args.length > 0 && !args[0].endsWith(".zip")) {
            String path = zipArchiver(args[0]);
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
                String path = zipArchiver(tempFile.toString());
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
                String path = unZipFile(tempFile.toString());
                System.out.println(path);
            }

        }
    }


    public static String zipArchiver(String fileIn) throws IOException {
        File fileToZip = new File(fileIn);
        String pathToZipFile = "C:/Users/phil/Desktop/compressed.zip";
        if (fileToZip.isFile()) {
            FileOutputStream fos = new FileOutputStream(pathToZipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.close();
            fis.close();
            fos.close();
            return pathToZipFile;
        } else {
            FileOutputStream fos = new FileOutputStream(pathToZipFile);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            return pathToZipFile;
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    public static String unZipFile(String fileZip) throws IOException {
        File destDir = new File(System.getProperty("user.dir"));
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        return destDir.toString();
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

}
