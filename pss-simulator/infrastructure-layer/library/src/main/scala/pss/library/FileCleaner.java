package pss.library;

import java.io.File;

public class FileCleaner {
    public static void cleanFiles(String directoryName, String prefix) {
        if (!FileUtility.fileExists(directoryName)) return;
        File directory = new File(directoryName);
        for (File file : directory.listFiles()) {
            if (file.getName().startsWith(prefix)) {
                file.delete();
            }
        }
    }
}