import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchiver {

    private static final int BUFFER_SIZE = 4096;

    public static String zipArchiver(String fileIn) throws IOException {
        File fileToZip = new File(fileIn);
        String pathToZipFile = "C:/Users/phil/Desktop/compressed.zip";
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
    }

    public static String ZipDirectory(String fileIn) throws IOException {
        File fileToZip = new File(fileIn);
        String pathToZipFile = "C:/Users/phil/Desktop/dirCompressed.zip";
        FileOutputStream fos = new FileOutputStream(pathToZipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
        return pathToZipFile;
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

    public static String extract(String path) throws IOException {
        File zipFile = new File(path);
        ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile));
        File target = new File(System.getProperty("user.dir"));
        try {
            ZipEntry entry;

            while ((entry = zip.getNextEntry()) != null) {
                File file = new File(target, entry.getName());

                if (!file.toPath().normalize().startsWith(target.toPath())) {
                    throw new IOException("Bad zip entry");
                }

                if (entry.isDirectory()) {
                    file.mkdirs();
                    continue;
                }

                byte[] buffer = new byte[BUFFER_SIZE];
                file.getParentFile().mkdirs();
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                int count;

                while ((count = zip.read(buffer)) != -1) {
                    out.write(buffer, 0, count);
                }

                out.close();
            }
        } finally {
            zip.close();
        }
        return target.toString();
    }

    public static String ZipMultipleFiles(List<String> srcFiles) throws IOException {
        String pathToZipFile = "C:/Users/phil/Desktop/multiCompressed.zip";
        FileOutputStream fos = new FileOutputStream(pathToZipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
        return pathToZipFile;
    }
}
