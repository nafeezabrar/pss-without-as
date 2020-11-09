package pss.library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FileUtility {

    public static void createDirectory(String directoryName) throws Exception {
        File file = new File(directoryName);
        if (file.exists())
            return;
        boolean directoryMade = file.mkdirs();
        if (!directoryMade) {
            throw new Exception("directory not made");
        }
    }

    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }


    public static FileWriter createFileWriteSafely(String fileName) throws IOException {
        File file = new File(fileName);
        return new FileWriter(file);
    }

    public static FileWriter createFileWriteSafelyInAppendMode(String fileName) throws IOException {
        File file = new File(fileName);
        return new FileWriter(file, true);
    }

    public File getFilePath(String fileName) throws URISyntaxException {
        URI uri = FileUtility.class.getClassLoader().getResource(fileName).toURI();
        return new File(uri);
    }

    public boolean existsFile(String fileName) {
        try {
            URI uri = FileUtility.class.getClassLoader().getResource(fileName).toURI();
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
