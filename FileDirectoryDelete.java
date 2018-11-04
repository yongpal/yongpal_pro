
/**
 * 2018. 10. 23
 * 디렉토리 생성 및 파일 삭제 하기 위한 Class
 * URL :
 */
import java.util.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FilenameFilter;

public class FileDirectoryDelete {

    public static void cleanDirectory(final File directory) throws IOException {
       final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for (final File file : files) {
            try {
                forceDelete(file);
                System.out.println("File Delete Load Status [Delete] -------");
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            final boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                final String message =
                        "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    private static File[] verifiedListFiles(File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }

    public static void deleteDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

       // if (!isSymlink(directory)) {
            cleanDirectory(directory);
        //}

        if (!directory.delete()) {
            final String message =
                    "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

        // 실행
    public static void main(String[] arg) {
        System.out.println("delete");
    }
}
