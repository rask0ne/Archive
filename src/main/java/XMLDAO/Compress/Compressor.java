package XMLDAO.Compress;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by rask on 16.04.2017.
 * Class for create ZIP archive and unZIP archive
 */
public class Compressor {

    /**
     * method for zipping file with filePath
     * @param filePath the path of file to zipping in zip archive
     * @param archivePath the path of creating archive
     * @param capacity the capacity of zipping. Borders: [0,9] - other - default capacity of zipping
     */
    public void compress(String filePath, String archivePath, int capacity) {
        try {
            File archive = new File(archivePath);
            if (archive.exists()) {
                archive.delete();
            }
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(archivePath));
            if (capacity >= 0 && capacity < 10) {
                out.setLevel(capacity);
            }
            File file = new File(filePath);
            if (file.exists()) {
                out.putNextEntry(new ZipEntry(file.getPath()));
                write(new FileInputStream(file), out);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * private method for write into zip archive
     * @param inputStream input stream of file for zipping
     * @param outputStream output stream of open zip archive
     * @throws IOException error with write/read from this streams
     */
    private static void write(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) >= 0)
            outputStream.write(buffer, 0, length);
        inputStream.close();
    }

    /**
     * method for decompress files from zip archive. Unzip all files in archive (without directories). Remove already exist files
     * @param archivePath the path of zip archive for unzipping. If zipping file is exist - remove the exist real file
     */
    public void decompress(String archivePath) {
        File file = new File(archivePath);
        if (!file.exists() || !file.canRead()) {
            System.out.println("File cannot be read");
            return;
        }
        try {
            ZipFile zip = new ZipFile(archivePath);
            Enumeration entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println(entry.getName());

                if (entry.isDirectory()) {
                    new File(file.getParent(), entry.getName()).mkdirs();
                } else {
                    dewrite(zip.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(
                                    new File(file.getParent(), entry.getName()))));
                }
            }
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * private method for output files from zip archive
     * @param in input stream of really file
     * @param out output stream from zipping file
     * @throws IOException error with this input/output streams
     */
    private static void dewrite(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}
